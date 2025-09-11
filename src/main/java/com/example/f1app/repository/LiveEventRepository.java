// src/main/java/com/example/f1app/repository/LiveEventRepository.java
package com.example.f1app.repository;

import com.example.f1app.model.LiveEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveEventRepository extends JpaRepository<LiveEvent, Long> {}
