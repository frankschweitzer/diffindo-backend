package com.diffindo.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @GetMapping("/test")
    public ResponseEntity<String> testing() {
        return ResponseEntity.ok("hello from secured endpoint");
    }
}
