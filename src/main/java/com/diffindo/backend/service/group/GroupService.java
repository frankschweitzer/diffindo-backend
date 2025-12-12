package com.diffindo.backend.service.group;

import com.diffindo.backend.consants.AppConstants;
import com.diffindo.backend.dto.GroupCreatedResponseDto;
import com.diffindo.backend.dto.GroupCreationRequestDto;
import com.diffindo.backend.dto.GroupFetchResponseDto;
import com.diffindo.backend.exceptions.UserNotFoundException;
import com.diffindo.backend.model.Group;
import com.diffindo.backend.model.Payment;
import com.diffindo.backend.model.User;
import com.diffindo.backend.repository.GroupRepository;
import com.diffindo.backend.repository.PaymentRepository;
import com.diffindo.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    private final AppConstants APP_CONSTANTS;
    private final GroupRepository groupRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public GroupCreatedResponseDto create(GroupCreationRequestDto groupCreationRequestDto) {
        var group = Group.builder()
                .merchant(groupCreationRequestDto.getMerchant())
                .totalCost(groupCreationRequestDto.getTotalCost())
                .groupPhoneNumbers(groupCreationRequestDto.getGroupPhoneNumbers())
                .status(APP_CONSTANTS.GROUP_PAYMENT_STATUS_PENDING)
                .build();

        // TODO: consider the order in which I save the group, validate users first? --> yes this is a bug
        // save group to groups table
        groupRepository.save(group);
        logger.info("group {} saved to GROUPS table", group.getGroupId());

        // create entries in payments table for each user
        Long individualAmount = (groupCreationRequestDto.getTotalCost() / groupCreationRequestDto.getGroupPhoneNumbers().size());
        for (String phoneNumber : groupCreationRequestDto.getGroupPhoneNumbers()) {
            Optional<User> currentUser = Optional.ofNullable(userRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new UserNotFoundException("Attempted to create a group but not all Users are registered.")));

            var payment = Payment.builder()
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

    public GroupFetchResponseDto fetchAll(String username) {
        // obtain userId from username
        Optional<User> requestingUser = userRepository.findByEmail(username);
        if (requestingUser.isEmpty()) {
            logger.info("userId could not be resolved from token");
            throw new UserNotFoundException("userId could not be resolved from token");
        }
        Long userId = requestingUser.get().getUserId();

        // TODO: this block may not be needed anymore
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User attempting to retrieve groups does not exist.")));

        Optional<List<Group>> groups = groupRepository.findByGroupPhoneNumbersContaining(user.get().getPhoneNumber());
        if (groups.isEmpty()) {
            return GroupFetchResponseDto.builder()
                    .build();
        }

        return GroupFetchResponseDto.builder()
                .groups(groups.get())
                .build();
    }
}
