package com.example.f1app.repository;

import com.example.f1app.model.Circuit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CircuitRepository extends JpaRepository<Circuit, Long> {
    Optional<Circuit> findByName(String name);
}
