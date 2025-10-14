package com.diffindo.backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreationDto {
    private String merchant;
    private Long totalCost;
    private int groupSize;
}
