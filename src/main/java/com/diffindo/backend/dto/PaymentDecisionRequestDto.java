package com.diffindo.backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDecisionRequestDto {
    private Long paymentId;
    private Long groupId;
    private Long userId;
    private boolean approved;
}
