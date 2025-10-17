package com.diffindo.backend.controller;

import com.diffindo.backend.dto.GroupCreatedResponseDto;
import com.diffindo.backend.dto.GroupCreationRequestDto;
import com.diffindo.backend.dto.GroupFetchRequestDto;
import com.diffindo.backend.dto.GroupFetchResponseDto;
import com.diffindo.backend.service.group.GroupCreationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    private final GroupCreationService groupCreationService;

    @PostMapping("/create")
    public ResponseEntity<GroupCreatedResponseDto> createGroup(@RequestBody GroupCreationRequestDto groupCreationDto) {
        logger.info("initiating group creation");
        return ResponseEntity.ok(groupCreationService.create(groupCreationDto));
    }

    @GetMapping("/fetch")
    public ResponseEntity<GroupFetchResponseDto> fetchGroups(@RequestBody GroupFetchRequestDto groupFetchRequestDto) {
        logger.info("initiating retrieval of all groups user is in");
        return ResponseEntity.ok(groupCreationService.fetchAll(groupFetchRequestDto));
    }
}
