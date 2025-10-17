package com.diffindo.backend.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreationDto {
    private String merchant;
    private Long totalCost;
    private List<String> groupPhoneNumbers;
}
