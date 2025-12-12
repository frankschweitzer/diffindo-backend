package com.diffindo.backend.repository;

import com.diffindo.backend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<List<Group>> findByGroupPhoneNumbersContaining(String phoneNumber);
}
