package com.example.f1app.service;

import com.example.f1app.model.User;
import com.example.f1app.repository.AppUserRepository;
import com.example.f1app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsletterService {

    private final AppUserRepository appUsers;
    private final UserRepository users;
    private final EmailService email;

    public NewsletterService(AppUserRepository appUsers, UserRepository users, EmailService email) {
        this.appUsers = appUsers;
        this.users = users;
        this.email = email;
    }

    /** Send weekly highlights to all users with a valid email. Returns send count. */
    public int sendToAll(List<String> highlights) {
        return users.findAll().stream()
            .mapToInt(u -> trySend(u, highlights))
            .sum();
    }

    /** Send weekly highlights only to subscribed users. Returns send count. */
    public int sendToSubscribed(List<String> highlights) {
        return users.findAll().stream()
            .filter(NewsletterService::isSubscribed)
            .mapToInt(u -> trySend(u, highlights))
            .sum();
    }

    /** Summary list of all users (id, email, name, subscribed). */
    public List<Map<String, Object>> getUserSummaries() {
        return users.findAll().stream()
            .map(u -> {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("id", u.getId());
                m.put("email", nullToEmpty(u.getUsername()));
                m.put("name", resolveName(u));
                m.put("subscribed", isSubscribed(u));
                return m;
            })
            .collect(Collectors.toList());
    }

    /** Quick counts for debug/admin use. */
    public Map<String, Object> debugStats() {
        List<User> all = users.findAll();
        long withEmail = all.stream().filter(u -> StringUtils.hasText(nullToEmpty(u.getUsername()))).count();
        long subscribed = all.stream().filter(NewsletterService::isSubscribed).count();
        return Map.of("users", all.size(), "withEmail", withEmail, "subscribed", subscribed);
    }

    // ── Internal helpers ─────────────────────────────────────────────────────

    private int trySend(User u, List<String> highlights) {
        String addr = nullToEmpty(u.getUsername());
        if (!StringUtils.hasText(addr)) return 0;
        try {
            email.sendWeeklyUpdate(addr, resolveName(u), highlights);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private String resolveName(User u) {
        String dn = nullToEmpty(u.getDisplayName());
        if (StringUtils.hasText(dn)) return dn;
        String username = nullToEmpty(u.getUsername());
        int at = username.indexOf('@');
        return (at > 0) ? username.substring(0, at) : (username.isEmpty() ? "F1 Fan" : username);
    }

    private static boolean isSubscribed(User u) {
        try {
            Method m = u.getClass().getMethod("isSubscribed");
            Object v = m.invoke(u);
            return Boolean.TRUE.equals(v);
        } catch (Exception ignored) {
            return false;
        }
    }

    private static String nullToEmpty(Object v) {
        return v == null ? "" : String.valueOf(v);
    }
}
