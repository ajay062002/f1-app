package com.example.f1app.controller;

import com.example.f1app.service.SummaryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin
public class AiController {
  private final SummaryService svc;
  public AiController(SummaryService svc){ this.svc = svc; }

  @PostMapping("/summary")
  public String summarize(@RequestBody String raw) {
    return svc.summarize(raw);
  }
}
