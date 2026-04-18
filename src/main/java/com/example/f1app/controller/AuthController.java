package com.example.f1app.controller;

import com.example.f1app.controller.dto.LoginRequest;
import com.example.f1app.model.AppUser;
import com.example.f1app.repository.AppUserRepository;
import com.example.f1app.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserRepository users;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserRepository users, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest req, HttpServletRequest request) {
        if (users.findByEmailIgnoreCase(req.email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "message", "Email already registered"));
        }

        AppUser user = new AppUser();
        user.setDisplayName(req.name);
        user.setEmail(req.email);
        user.setPasswordHash(passwordEncoder.encode(req.password));
        users.save(user);

        // Send welcome email
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            emailService.sendWelcomeEmail(user.getEmail(), user.getDisplayName());
        }

        // Auto-login after register
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", user.getId());

        return ResponseEntity.ok(Map.of("ok", true, "id", user.getId()));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest req, HttpServletRequest request) {
        AppUser user = users.findByEmailIgnoreCase(req.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(401).body(Map.of("ok", false, "message", "Invalid email or password"));
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("userId", user.getId());

        return ResponseEntity.ok(Map.of(
                "ok", true,
                "email", user.getEmail(),
                "displayName", user.getDisplayName() != null ? user.getDisplayName() : ""
        ));
    }

    @GetMapping("/me")
    public Map<String, Object> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return Map.of("loggedIn", false);
        }

        Long userId = (Long) session.getAttribute("userId");
        AppUser user = users.findById(userId).orElse(null);
        if (user == null) {
            return Map.of("loggedIn", false);
        }

        return Map.of(
                "loggedIn", true,
                "name", user.getDisplayName() != null ? user.getDisplayName() : "",
                "email", user.getEmail(),
                "role", user.getRole() != null ? user.getRole() : "USER"
        );
    }

    public static class RegisterRequest {
        public String name;
        public String email;
        public String password;
    }
}
