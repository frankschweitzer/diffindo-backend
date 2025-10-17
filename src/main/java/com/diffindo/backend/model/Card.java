package com.diffindo.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "cards")
public class Card {
    /**
     * TODO --> consider placing this in the USERS table to make it easier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;
}
