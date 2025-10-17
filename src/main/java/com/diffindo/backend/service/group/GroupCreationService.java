package com.diffindo.backend.service.group;

import com.diffindo.backend.consants.AppConstants;
import com.diffindo.backend.controller.GroupController;
import com.diffindo.backend.dto.GroupCreatedResponseDto;
import com.diffindo.backend.dto.GroupCreationDto;
import com.diffindo.backend.model.Group;
import com.diffindo.backend.model.Payments;
import com.diffindo.backend.model.User;
import com.diffindo.backend.repository.GroupRepository;
import com.diffindo.backend.repository.PaymentRepository;
import com.diffindo.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupCreationService {

    private static final Logger logger = LoggerFactory.getLogger(GroupCreationService.class);

    private final AppConstants APP_CONSTANTS;
    private final GroupRepository groupRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public GroupCreatedResponseDto create(GroupCreationDto groupCreationDto) {
        var group = Group.builder()
                .merchant(groupCreationDto.getMerchant())
                .totalCost(groupCreationDto.getTotalCost())
                .groupPhoneNumbers(groupCreationDto.getGroupPhoneNumbers())
                .status(APP_CONSTANTS.GROUP_PAYMENT_STATUS_PENDING)
                .build();

        // TODO: consider the order in which I save the group --> should I validate users first?

        // save group to groups table
        groupRepository.save(group);
        logger.info("group {} saved to GROUPS table", group.getGroupId());

        // create entries in payments table for each user
        Long individualAmount = (groupCreationDto.getTotalCost() / groupCreationDto.getGroupPhoneNumbers().size());
        for (String phoneNumber : groupCreationDto.getGroupPhoneNumbers()) {
            Optional<User> currentUser = Optional.ofNullable(userRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new UsernameNotFoundException("user does not exist")));

            var payment = Payments.builder()
                    .groupId(group.getGroupId())
                    .userId(currentUser.get().getUserId())
                    .individualAmount(individualAmount)
                    .status(APP_CONSTANTS.INDIVIDUAL_PAYMENT_STATUS_PENDING)
                    .build();

            paymentRepository.save(payment);
            logger.info("payment {} for {} saved to PAYMENTS table", payment.getPaymentId(), phoneNumber);
        }

        return GroupCreatedResponseDto.builder()
                .groupId(group.getGroupId())
                .build();
    }
}
