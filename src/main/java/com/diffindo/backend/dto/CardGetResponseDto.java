package com.diffindo.backend.dto;

import com.diffindo.backend.model.Card;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardGetResponseDto {
    private List<Card> cards;
}
