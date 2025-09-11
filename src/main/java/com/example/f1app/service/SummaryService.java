package com.example.f1app.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class SummaryService {
  public String summarize(String raw) {
    if (raw == null || raw.isBlank()) return "No data to summarize.";
    // naive: split lines, keep top 5, normalize
    var points = Arrays.stream(raw.split("\\r?\\n"))
        .map(s -> s.trim())
        .filter(s -> !s.isBlank())
        .limit(5)
        .map(s -> "• " + s.replaceAll("\\s+", " "))
        .collect(Collectors.joining("\n"));
    return "Key Moments:\n" + points;
  }
}
