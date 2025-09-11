package com.example.f1app.controller;

import com.example.f1app.model.Team;
import com.example.f1app.repository.TeamRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/teams") @CrossOrigin
public class TeamController {
  private final TeamRepository repo;
  public TeamController(TeamRepository repo){ this.repo=repo; }

  @GetMapping public List<Team> all(){ return repo.findAll(); }
  @PostMapping public Team create(@RequestBody Team t){ return repo.save(t); }
}
