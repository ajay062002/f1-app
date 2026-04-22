package com.example.f1app.controller;

import com.example.f1app.service.NewsletterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/newsletter")
@CrossOrigin
public class NewsletterController {

    private final NewsletterService newsletter;

    public NewsletterController(NewsletterService newsletter) {
        this.newsletter = newsletter;
    }

    @PostMapping("/send-weekly")
    public ResponseEntity<String> sendWeekly(@RequestBody List<String> highlights) {
        int n = newsletter.sendToAll(highlights);
        return ResponseEntity.ok("Weekly email sent to " + n + " user(s).");
    }

    @PostMapping("/send-weekly-subscribed")
    public ResponseEntity<String> sendWeeklySubscribed(@RequestBody List<String> highlights) {
        int n = newsletter.sendToSubscribed(highlights);
        return ResponseEntity.ok("Weekly email (subscribed) sent to " + n + " user(s).");
    }

    @GetMapping("/all-users")
    public List<Map<String, Object>> allUsers() {
        return newsletter.getUserSummaries();
    }

    @GetMapping("/debug")
    public Map<String, Object> debug() {
        return newsletter.debugStats();
    }
}
