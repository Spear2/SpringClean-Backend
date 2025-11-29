package com.project.SpringClean.repository;

import com.project.SpringClean.model.Booking;
import com.project.SpringClean.model.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CleanerRepository extends JpaRepository<Cleaner, Long> {
    boolean existsByEmail(String email);
    Optional<Cleaner> findByEmail(String email);
}