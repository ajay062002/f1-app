package com.example.f1app.controller;

import com.example.f1app.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test/mail")
public class MailTestController {

    private final EmailService emailService;

    public MailTestController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * GET /api/test/mail/send?to=someone@example.com
     */
    @GetMapping("/send")
    public Object send(@RequestParam String to) {
        String subject = "Test from Aj F1 Live";
        String html = "<h3>It works!</h3><p>Your SMTP is configured 👍</p>";
        emailService.sendHtml(to, subject, html); // or sendSimple(...) if that's your method
        return java.util.Map.of("ok", true, "to", to);
    }
}
