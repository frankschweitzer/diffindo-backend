package com.diffindo.backend.controller;

import com.diffindo.backend.dto.UserLoginDto;
import com.diffindo.backend.dto.UserRegistrationDto;
import com.diffindo.backend.service.user.UserService;
import com.diffindo.backend.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        logger.info("initiating user registration");

        userService.registerUser(userRegistrationDto.getName(), userRegistrationDto.getPhoneNumber(), userRegistrationDto.getEmail(), userRegistrationDto.getPassword());

        logger.info("user registration completed successfully");
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUserAndGetToken(@RequestBody UserLoginDto userLoginDto) {
        logger.info("initiating user login");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword())
        );

        final String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        final String jwt = jwtUtil.generateToken(email);

        logger.info("user login and token retrieval success");
        return new ResponseEntity<>(Map.of("token", jwt).toString(), HttpStatus.OK);
    }
}
