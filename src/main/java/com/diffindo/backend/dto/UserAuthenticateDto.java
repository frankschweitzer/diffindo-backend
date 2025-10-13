package com.diffindo.backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticateDto {
    private String email;
    private String password;
}
