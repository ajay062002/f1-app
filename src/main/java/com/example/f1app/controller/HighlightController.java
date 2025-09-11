package com.example.f1app.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/highlights")
@CrossOrigin
public class HighlightController {

  public record Highlight(String title, String subtitle, String img, String link) {}

  @GetMapping
  public List<Highlight> latest() {
    return List.of(
      new Highlight("Verstappen wins at Monza", "RB20 dominates the field",
        "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=1600&auto=format","/standings.html"),
      new Highlight("Ferrari double-podium", "Tifosi go wild at home",
        "https://images.unsplash.com/photo-1520975916090-3105956dac38?w=1600&auto=format","/teams.html"),
      new Highlight("Perez edges Leclerc", "Thriller in final laps",
        "https://images.unsplash.com/photo-1511735111819-9a3f7709049c?w=1600&auto=format","/drivers.html")
    );
  }
}
