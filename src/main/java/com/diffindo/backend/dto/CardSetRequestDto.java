package com.diffindo.backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardSetRequestDto {
    private String PAN;
    private String expirationDate;
    private String cvv;
}
