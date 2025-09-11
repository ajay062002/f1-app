package com.example.f1app.controller;

import com.example.f1app.service.EmailService;
import com.example.f1app.service.WeeklyDigestJob;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestEmailController {

    private final EmailService emailService;
    private final WeeklyDigestJob weeklyDigestJob;

    public TestEmailController(EmailService emailService, WeeklyDigestJob weeklyDigestJob) {
        this.emailService = emailService;
        this.weeklyDigestJob = weeklyDigestJob;
    }

    /**
     * Quick single-recipient test:
     * GET /api/test/send?to=you@example.com
     */
    @GetMapping("/send")
    public Map<String, Object> send(@RequestParam String to) {
        String subject = "Test from Aj F1 Live";
        String html = "<h3>It works!</h3><p>Your SMTP is configured 👍</p>";
        emailService.sendCustomEmail(to, subject, html);
        return Map.of("ok", true, "to", to);
    }

    /**
     * Instant manual trigger for the weekly digest:
     * GET /api/test/digest
     */
    @GetMapping("/digest")
    public Map<String, Object> runDigestNow() {
        weeklyDigestJob.sendWeeklyRaceDigest();
        return Map.of("ok", true, "message", "Weekly digest executed manually");
    }
}
