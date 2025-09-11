package com.example.f1app.controller;

import com.example.f1app.repository.AppUserRepository;
import com.example.f1app.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/newsletter")
public class AdminNewsletterController {

    private final AppUserRepository users;
    private final EmailService emailService;

    public AdminNewsletterController(AppUserRepository users, EmailService emailService) {
        this.users = users;
        this.emailService = emailService;
    }

    /**
     * POST /api/admin/newsletter/send
     * x-www-form-urlencoded:
     *   subject=...&html=...
     * Sends one email with ALL recipients in BCC.
     */
    @PostMapping("/send")
    public Map<String, Object> sendToAll(
            @RequestParam String subject,
            @RequestParam String html) {

        // pull non-empty emails from DB
        List<String> emails = users.findAll().stream()
                .map(u -> u.getEmail())
                .filter(e -> e != null && !e.isBlank())
                .distinct()
                .toList();

        int sent = 0;
        if (!emails.isEmpty()) {
            // Your EmailService already supports HTML + BCC in most setups.
            // If you only have a "sendHtml(to,...)" method, loop them individually instead.
            emailService.sendBulkBcc(emails, subject, html);
            sent = emails.size();
        }
        return Map.of("ok", true, "recipients", sent);
    }
}
