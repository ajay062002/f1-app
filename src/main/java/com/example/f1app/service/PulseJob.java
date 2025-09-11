package com.example.f1app.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PulseJob {
  private final LiveUpdateService live;
  public PulseJob(LiveUpdateService live){ this.live = live; }

  // Demo heartbeat every 60s (replace with API polling and diffing)
  @Scheduled(fixedRate = 60000)
  public void heartbeat() {
    live.broadcast("⏱️ Update check …");
  }
}
