package com.example.f1app.controller;

import com.example.f1app.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notify")
public class EmailController {

    private final EmailService email;

    public EmailController(EmailService email) {
        this.email = email;
    }

    @PostMapping("/weekly")
    public String sendWeekly(@RequestParam String to, @RequestParam String name) {
        // demo payload — swap for your real highlights if you have them
        List<String> highlights = List.of("Quali recap", "Race pace notes", "Driver standings update");
        email.sendWeeklyUpdate(to, name, highlights);
        return "ok";
    }
}
