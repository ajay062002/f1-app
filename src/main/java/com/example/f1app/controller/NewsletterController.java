package com.example.f1app.controller;

import com.example.f1app.model.User;
import com.example.f1app.repository.UserRepository;
import com.example.f1app.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/newsletter")
@CrossOrigin
public class NewsletterController {

    private final UserRepository users;
    private final EmailService email;

    public NewsletterController(UserRepository users, EmailService email) {
        this.users = users;
        this.email = email;
    }

    // ---- 1) Send the weekly email to ALL users that have a non-empty email
    @PostMapping("/send-weekly")
    public ResponseEntity<String> sendWeekly(@RequestBody List<String> highlights) {
        int n = users.findAll().stream()
                .mapToInt(u -> trySend(u, highlights))
                .sum();
        return ResponseEntity.ok("Weekly email sent to " + n + " user(s).");
    }

    // ---- 2) Send the weekly email but only to users who are subscribed (if that field exists)
    @PostMapping("/send-weekly-subscribed")
    public ResponseEntity<String> sendWeeklySubscribed(@RequestBody List<String> highlights) {
        int n = users.findAll().stream()
                .filter(NewsletterController::isSubscribedSafe)
                .mapToInt(u -> trySend(u, highlights))
                .sum();
        return ResponseEntity.ok("Weekly email (subscribed) sent to " + n + " user(s).");
    }

    // ---- 3) List users (simple view)
    @GetMapping("/all-users")
    public List<Map<String, Object>> allUsers() {
        return users.findAll().stream()
                .map(u -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("id", u.getId());
                    m.put("email", safeStr(u.getUsername()));      // email is stored in username
                    m.put("name", chooseName(u));
                    m.put("subscribed", safeBool(u));               // true/false/false-when-missing
                    return m;
                })
                .collect(Collectors.toList());
    }

    // ---- 4) Tiny debug counts
    @GetMapping("/debug")
    public Map<String, Object> debug() {
        long total = users.count();
        long withEmail = users.findAll().stream()
                .filter(u -> StringUtils.hasText(safeStr(u.getUsername())))
                .count();
        long subscribed = users.findAll().stream()
                .filter(NewsletterController::isSubscribedSafe)
                .count();

        return Map.of("users", total, "withEmail", withEmail, "subscribed", subscribed);
    }

    // ===== helpers =====
    private int trySend(User u, List<String> highlights) {
        String emailAddr = safeStr(u.getUsername());
        if (!StringUtils.hasText(emailAddr)) return 0;

        String name = chooseName(u);
        try {
            email.sendWeeklyUpdate(emailAddr, name, highlights);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private String chooseName(User u) {
        // Prefer display name if available
        String dn = safeStr(u.getDisplayName());
        if (StringUtils.hasText(dn)) return dn;

        // Fallback to the part before @ in the username/email
        String username = safeStr(u.getUsername());
        if (StringUtils.hasText(username)) {
            int at = username.indexOf('@');
            return at > 0 ? username.substring(0, at) : username;
        }

        return "F1 Fan";
    }

    private static boolean isSubscribedSafe(User u) {
        Boolean b = safeBool(u);
        return b != null && b;
    }

    private static Boolean safeBool(User u) {
        // Works whether or not the User class actually has an `isSubscribed()` method
        try {
            Method m = u.getClass().getMethod("isSubscribed");
            Object v = m.invoke(u);
            return (v instanceof Boolean) ? (Boolean) v : null;
        } catch (Exception ignored) {
            return null; // field/method missing
        }
    }

    private static String safeStr(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
