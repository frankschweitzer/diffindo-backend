package com.diffindo.backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDecisionRequestDto {
    private Long groupId;
    private boolean approved;
}
