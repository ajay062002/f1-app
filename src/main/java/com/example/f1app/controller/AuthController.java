package com.example.f1app.controller;

import com.example.f1app.model.AppUser;
import com.example.f1app.repository.AppUserRepository;
import com.example.f1app.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserRepository users;
    private final EmailService emailService;

    public AuthController(AppUserRepository users, EmailService emailService) {
        this.users = users;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterRequest req) {
        // Save user
        AppUser user = new AppUser();
        user.setDisplayName(req.name);          // use displayName instead of name
        user.setEmail(req.email);
        user.setPasswordHash(req.password);     // ⚠️ replace with real hashing
        users.save(user);

        // Send welcome mail (async)
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            System.out.println("📧 Sending welcome mail to " + user.getEmail());
            emailService.sendWelcomeEmail(user.getEmail(), user.getDisplayName());
        }

        return Map.of("ok", true, "id", user.getId());
    }

    public static class RegisterRequest {
        public String name;
        public String email;
        public String password;
    }
}
