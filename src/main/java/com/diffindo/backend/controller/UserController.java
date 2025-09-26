package com.diffindo.backend.controller;

import com.diffindo.backend.dto.UserRegistrationDto;
import com.diffindo.backend.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        logger.info("initiating user registration");
        userService.registerUser(userRegistrationDto.getName(), userRegistrationDto.getPhoneNumber(), userRegistrationDto.getEmail(), userRegistrationDto.getPassword());
        logger.info("user registration completed successfully");
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
}
