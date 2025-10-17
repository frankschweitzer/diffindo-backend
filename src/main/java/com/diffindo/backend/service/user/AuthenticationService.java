package com.diffindo.backend.service.user;

import com.diffindo.backend.dto.UserAuthenticationResponseDto;
import com.diffindo.backend.dto.UserAuthenticateRequestDto;
import com.diffindo.backend.dto.UserRegistrationRequestDto;
import com.diffindo.backend.model.Role;
import com.diffindo.backend.model.User;
import com.diffindo.backend.repository.UserRepository;
import com.diffindo.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    private final AuthenticationManager authenticationManager;

    public UserAuthenticationResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto) {
        var user = User.builder()
                .name(userRegistrationRequestDto.getName())
                .email(userRegistrationRequestDto.getEmail())
                .phoneNumber(userRegistrationRequestDto.getPhoneNumber())
                .password(passwordEncoder.encode(userRegistrationRequestDto.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        logger.info("user saved to USERS table");

        var jwtToken = jwtService.generateToken(user);
        return UserAuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public UserAuthenticationResponseDto authenticate(UserAuthenticateRequestDto userAuthenticateRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthenticateRequestDto.getEmail(),
                        userAuthenticateRequestDto.getPassword()
                )
        );

        var user = userRepository.findByEmail(userAuthenticateRequestDto.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return UserAuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

}
