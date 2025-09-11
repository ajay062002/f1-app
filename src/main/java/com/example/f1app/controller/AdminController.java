package com.example.f1app.controller;

import com.example.f1app.model.LiveEvent;
import com.example.f1app.repository.LiveEventRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private final LiveEventRepository liveEvents;

    public AdminController(LiveEventRepository liveEvents) {
        this.liveEvents = liveEvents;
    }

    private boolean isAdmin(HttpSession session) {
        Object role = session.getAttribute("role");
        return role != null && "ADMIN".equals(String.valueOf(role));
    }

    @PostMapping("/push")
    public ResponseEntity<?> push(@RequestBody Map<String, String> body, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(Map.of("message", "Admin only"));
        }

        String message = body.getOrDefault("message", "").trim();
        if (!StringUtils.hasText(message)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Empty message"));
        }

        LiveEvent ev = new LiveEvent();
        ev.setMessage(message);
        ev.setCreatedAt(Instant.now());
        liveEvents.save(ev);

        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
