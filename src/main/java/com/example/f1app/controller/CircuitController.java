package com.example.f1app.controller;

import com.example.f1app.model.Circuit;
import com.example.f1app.repository.CircuitRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/circuits")
@CrossOrigin(origins = "*")
public class CircuitController {

    private final CircuitRepository repo;

    public CircuitController(CircuitRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Circuit> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Circuit one(@PathVariable Long id) { return repo.findById(id).orElseThrow(); }
}
