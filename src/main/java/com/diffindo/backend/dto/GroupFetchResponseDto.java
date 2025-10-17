package com.diffindo.backend.dto;

import com.diffindo.backend.model.Group;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupFetchResponseDto {
    private List<Group> groups;
}
