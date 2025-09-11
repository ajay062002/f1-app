package com.example.f1app.repository;

import com.example.f1app.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
  List<Driver> findByLastNameIgnoreCaseContainingOrFirstNameIgnoreCaseContaining(String last, String first);
}
