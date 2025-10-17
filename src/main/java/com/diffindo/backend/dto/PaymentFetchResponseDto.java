package com.diffindo.backend.dto;

import com.diffindo.backend.model.Payment;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFetchResponseDto {
    private List<Payment> payments;
}
