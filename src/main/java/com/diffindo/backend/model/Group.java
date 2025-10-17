package com.diffindo.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(nullable = false)
    private String merchant;

    @Column(nullable = false)
    private Long totalCost;

    @ElementCollection
    private List<String> groupPhoneNumbers;

    @Column(nullable = false)
    private String status;
}
