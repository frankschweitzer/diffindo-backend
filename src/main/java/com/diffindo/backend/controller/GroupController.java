package com.diffindo.backend.controller;

import com.diffindo.backend.dto.GroupCreatedResponseDto;
import com.diffindo.backend.dto.GroupCreationDto;
import com.diffindo.backend.service.group.GroupStateService;
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

    private final GroupStateService groupStateService;

    @PostMapping("/create")
    public ResponseEntity<GroupCreatedResponseDto> createGroup(@RequestBody GroupCreationDto groupCreationDto) {
        logger.info("initiating group creation");
        return ResponseEntity.ok(groupStateService.create(groupCreationDto));
    }
}
