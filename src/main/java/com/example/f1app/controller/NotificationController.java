package com.example.f1app.controller;

import com.example.f1app.model.AppUser;
import com.example.f1app.repository.AppUserRepository;
import com.example.f1app.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notify")
@CrossOrigin
public class NotificationController {

    private final AppUserRepository users;
    private final EmailService email;

    public NotificationController(AppUserRepository users, EmailService email) {
        this.users = users;
        this.email = email;
    }

    @PostMapping("/all")
    public Map<String, Object> sendToAll(@RequestParam String subject,
                                         @RequestParam(required = false, defaultValue = "Hello from F1 Portal!") String message) {

        List<String> emails = users.findAll().stream()
                .map(AppUser::getEmail)
                .toList();

        String when = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z")
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());

        String html = """
                <html>
                  <body style="font-family: system-ui, Segoe UI, Roboto, Arial">
                    <h2>F1 Portal Update</h2>
                    <p>%s</p>
                    <hr/>
                    <p style="font-size:12px;color:#666">Sent %s • You’re receiving this because you have an account on F1 Portal.</p>
                  </body>
                </html>
                """.formatted(message, when);

        int count = email.sendBulkBcc(emails, subject, html);
        return Map.of("status", "ok", "count", count);
    }
}
