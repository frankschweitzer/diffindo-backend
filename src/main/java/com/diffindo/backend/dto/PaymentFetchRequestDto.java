package com.diffindo.backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFetchRequestDto {
    // TODO --> use JWT to determine user of request
    private Long userId;
}
