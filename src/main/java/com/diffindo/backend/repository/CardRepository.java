package com.diffindo.backend.repository;

import com.diffindo.backend.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    /**
     * TODO
     */
}
