package com.diffindo.backend.service.group;

import com.diffindo.backend.consants.AppConstants;
import com.diffindo.backend.dto.GroupCreatedResponseDto;
import com.diffindo.backend.dto.GroupCreationDto;
import com.diffindo.backend.model.Group;
import com.diffindo.backend.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupStateService {

    private final AppConstants APP_CONSTANTS;
    private final GroupRepository groupRepository;

    public GroupCreatedResponseDto create(GroupCreationDto groupCreationDto) {
        var group = Group.builder()
                .merchant(groupCreationDto.getMerchant())
                .totalCost(groupCreationDto.getTotalCost())
                .groupPhoneNumbers(groupCreationDto.getGroupPhoneNumbers())
                .status(APP_CONSTANTS.GROUP_STATUS_PENDING)
                .build();

        groupRepository.save(group);

        return GroupCreatedResponseDto.builder()
                .groupId(group.getGroupId())
                .build();
    }
}
