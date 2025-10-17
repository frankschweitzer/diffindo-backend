package com.diffindo.backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequestDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
}
