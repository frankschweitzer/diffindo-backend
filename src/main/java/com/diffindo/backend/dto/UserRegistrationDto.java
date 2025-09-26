package com.diffindo.backend.dto;

import lombok.*;

@Getter
@Setter
public class UserRegistrationDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
}
