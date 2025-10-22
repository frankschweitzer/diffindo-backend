package com.diffindo.backend.service.payment;

import com.diffindo.backend.consants.AppConstants;
import com.diffindo.backend.dto.PaymentDecisionRequestDto;
import com.diffindo.backend.dto.PaymentDecisionResponseDto;
import com.diffindo.backend.dto.PaymentFetchRequestDto;
import com.diffindo.backend.dto.PaymentFetchResponseDto;
import com.diffindo.backend.exceptions.GroupNotFoundException;
import com.diffindo.backend.exceptions.PaymentNotFoundException;
import com.diffindo.backend.exceptions.UserNotFoundException;
import com.diffindo.backend.model.Group;
import com.diffindo.backend.model.Payment;
import com.diffindo.backend.model.User;
import com.diffindo.backend.repository.GroupRepository;
import com.diffindo.backend.repository.PaymentRepository;
import com.diffindo.backend.repository.UserRepository;
import com.diffindo.backend.service.stripe.StripeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentStatusService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentStatusService.class);

    private final AppConstants APP_CONSTANTS;
    private final PaymentRepository paymentRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    private final StripeService stripeService;

    public PaymentDecisionResponseDto executePaymentDecision(PaymentDecisionRequestDto paymentDecisionRequestDto) {
        // ensure payment exists
        Optional<Payment> payment = paymentRepository.findById(paymentDecisionRequestDto.getPaymentId());
        if (payment.isEmpty()) {
            logger.info("payment {} does not exist", paymentDecisionRequestDto.getPaymentId());
            throw new PaymentNotFoundException("paymentId: " + paymentDecisionRequestDto.getPaymentId() + " does not exist");
        }

        // update payment status in PAYMENTS table
        var updatedPayment = Payment.builder()
                .paymentId(payment.get().getPaymentId())
                .groupId(payment.get().getGroupId())
                .userId(payment.get().getUserId())
                .individualAmount(payment.get().getIndividualAmount())
                .status(paymentDecisionRequestDto.isApproved() ? APP_CONSTANTS.INDIVIDUAL_PAYMENT_STATUS_APPROVED : APP_CONSTANTS.INDIVIDUAL_PAYMENT_STATUS_DECLINED)
                .build();
        paymentRepository.save(updatedPayment);
        // TODO: if it was approved then initiate a hold on funds
        stripeService.placeHoldOnCard();

        // extract group
        Optional<Group> group = groupRepository.findById(paymentDecisionRequestDto.getGroupId());
        if (group.isEmpty()) {
            logger.info("group {} does not exist", paymentDecisionRequestDto.getGroupId());
            throw new GroupNotFoundException("Group " + paymentDecisionRequestDto.getGroupId() + " does not exist");
        }

        // if it was a decline initiate group status to DECLINED in GROUPS table
        if (!paymentDecisionRequestDto.isApproved()) {
            var declinedGroup = Group.builder()
                    .groupId(group.get().getGroupId())
                    .merchant(group.get().getMerchant())
                    .totalCost(group.get().getTotalCost())
                    .groupPhoneNumbers(group.get().getGroupPhoneNumbers())
                    .status(APP_CONSTANTS.GROUP_PAYMENT_STATUS_DECLINED)
                    .build();
            groupRepository.save(declinedGroup);
            return PaymentDecisionResponseDto.builder()
                    .message("Successfully declined request, GroupID " + group.get().getGroupId() + " is now declined")
                    .build();
        }

        // if it was approved initiate group check to see if the remaining users have all accepted
        for (String phoneNumber : group.get().getGroupPhoneNumbers()) {
            // get userID from phoneNumber
            Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
            if (user.isEmpty()) {
                logger.info("user with phoneNumber {} does not exist", phoneNumber);
                throw new UserNotFoundException("user with phoneNumber: " + phoneNumber + " - does not exist");
            }

            // search for userId & groupId in PAYMENTS table
            Optional<Payment> userPayment = paymentRepository.findPaymentByGroupIdAndUserId(group.get().getGroupId(), user.get().getUserId());
            if (userPayment.isEmpty()) {
                logger.info("user payment does not exist in group");
                throw new PaymentNotFoundException("userId " + user.get().getUserId() + " is not in the following Group: " + group.get().getGroupId());
            }

            // if a user is still pending then return
            if (userPayment.get().getStatus().equals(APP_CONSTANTS.INDIVIDUAL_PAYMENT_STATUS_PENDING)) {
                return PaymentDecisionResponseDto.builder()
                        .message("Successfully approved request, GroupID " + group.get().getGroupId() + " is still awaiting approvals")
                        .build();
            }
        }

        // all members have approved --> update status in GROUPS table to APPROVED
        var approvedGroup = Group.builder()
                .groupId(group.get().getGroupId())
                .merchant(group.get().getMerchant())
                .totalCost(group.get().getTotalCost())
                .groupPhoneNumbers(group.get().getGroupPhoneNumbers())
                .status(APP_CONSTANTS.GROUP_PAYMENT_STATUS_APPROVED)
                .build();
        groupRepository.save(approvedGroup);
        // TODO: initiate the execution on the hold for all cards in the group
        stripeService.initiatePayment();

        return PaymentDecisionResponseDto.builder()
                .message("Successfully approved request, GroupID " + group.get().getGroupId() + " has all required approvals and will initiate purchase.")
                .build();
    }

    public PaymentFetchResponseDto fetchAllPayments(PaymentFetchRequestDto paymentFetchRequestDto) {
        Optional<List<Payment>> payments = paymentRepository.findAllByUserId(paymentFetchRequestDto.getUserId());
        return payments.map(paymentList -> PaymentFetchResponseDto.builder()
                .payments(paymentList)
                .build()).orElse(null);
    }

}
