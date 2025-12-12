package com.diffindo.backend.repository;

import com.diffindo.backend.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<List<Card>> findAllByUserId(Long userId);
}
