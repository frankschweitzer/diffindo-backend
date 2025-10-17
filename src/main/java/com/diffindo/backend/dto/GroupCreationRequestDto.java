package com.diffindo.backend.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreationRequestDto {
    private String merchant;
    private Long totalCost;
    private List<String> groupPhoneNumbers;
}
