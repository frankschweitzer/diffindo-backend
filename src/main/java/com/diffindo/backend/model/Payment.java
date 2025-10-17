package com.diffindo.backend.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long groupId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long individualAmount;

    @Column(nullable = false)
    private String status;
}
