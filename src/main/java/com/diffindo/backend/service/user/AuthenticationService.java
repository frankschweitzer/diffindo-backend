package com.diffindo.backend.service.user;

import com.diffindo.backend.dto.UserAuthenticationResponseDto;
import com.diffindo.backend.dto.UserAuthenticateDto;
import com.diffindo.backend.dto.UserRegistrationDto;
import com.diffindo.backend.model.Role;
import com.diffindo.backend.model.User;
import com.diffindo.backend.repository.UserRepository;
import com.diffindo.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    private final AuthenticationManager authenticationManager;

    public UserAuthenticationResponseDto register(UserRegistrationDto userRegistrationDto) {
        var user = User.builder()
                .name(userRegistrationDto.getName())
                .email(userRegistrationDto.getEmail())
                .phoneNumber(userRegistrationDto.getPhoneNumber())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return UserAuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public UserAuthenticationResponseDto authenticate(UserAuthenticateDto userAuthenticateDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthenticateDto.getEmail(),
                        userAuthenticateDto.getPassword()
                )
        );

        var user = userRepository.findByEmail(userAuthenticateDto.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return UserAuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

}
