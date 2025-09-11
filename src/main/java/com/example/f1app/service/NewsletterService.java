package com.example.f1app.service;

import com.example.f1app.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsletterService {

    private final AppUserRepository users;
    private final EmailService email;

    public NewsletterService(AppUserRepository users, EmailService email) {
        this.users = users;
        this.email = email;
    }

    /** Sends a newsletter email to all users (as a single BCC). Returns count. */
    public int sendToAll(String subject, String html) {
        List<String> bcc = users.findAll().stream()
                .map(u -> u.getEmail())
                .filter(e -> e != null && !e.isBlank())
                .toList();
        return email.sendBulkBcc(bcc, subject, html);
    }
}
