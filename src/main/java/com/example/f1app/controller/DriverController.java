package com.example.f1app.controller;

import com.example.f1app.model.Driver;
import com.example.f1app.repository.DriverRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/drivers") @CrossOrigin
public class DriverController {
  private final DriverRepository repo;
  public DriverController(DriverRepository repo){ this.repo=repo; }

  @GetMapping
  public List<Driver> all(@RequestParam(value="q", required=false) String q){
    if(q==null || q.isBlank()) return repo.findAll();
    return repo.findByLastNameIgnoreCaseContainingOrFirstNameIgnoreCaseContaining(q,q);
  }

  @PostMapping public Driver create(@RequestBody Driver d){ return repo.save(d); }
}
