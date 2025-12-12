package com.diffindo.backend.controller;

import com.diffindo.backend.dto.GroupCreatedResponseDto;
import com.diffindo.backend.dto.GroupCreationRequestDto;
//import com.diffindo.backend.dto.GroupFetchRequestDto;
import com.diffindo.backend.dto.GroupFetchResponseDto;
import com.diffindo.backend.exceptions.BadTokenException;
import com.diffindo.backend.service.group.GroupService;
import com.diffindo.backend.util.JwtUtil;
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

    private final GroupService groupService;
    private final JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<GroupCreatedResponseDto> createGroup(@RequestBody GroupCreationRequestDto groupCreationRequestDto) {
        logger.info("initiating group creation");
        return ResponseEntity.ok(groupService.create(groupCreationRequestDto));
    }

    @GetMapping("/fetch")
    public ResponseEntity<GroupFetchResponseDto> fetchGroups(
            @RequestHeader("Authorization") String authToken
    ) {
        logger.info("extracting username from token");
        String[] tokenParts = authToken.split(" ");
        if (tokenParts.length != 2) {
            logger.info("invalid token when requesting to fetch all payments");
            throw new BadTokenException("bad authentication token");
        }
        String username = jwtUtil.extractUsername(tokenParts[1]);

        logger.info("initiating retrieval of all groups user is in");
        return ResponseEntity.ok(groupService.fetchAll(username));
    }
}
