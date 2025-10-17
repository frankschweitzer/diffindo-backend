package com.diffindo.backend.controller;

import com.diffindo.backend.dto.UserAuthenticationResponseDto;
import com.diffindo.backend.dto.UserAuthenticateDto;
import com.diffindo.backend.dto.UserRegistrationDto;
import com.diffindo.backend.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserAuthenticationResponseDto> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        logger.info("initiating user registration");
        return ResponseEntity.ok(authenticationService.register(userRegistrationDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponseDto> loginUserAndGetToken(@RequestBody UserAuthenticateDto userAuthenticateDto) {
        logger.info("initiating user login");
        return ResponseEntity.ok(authenticationService.authenticate(userAuthenticateDto));
    }
}
