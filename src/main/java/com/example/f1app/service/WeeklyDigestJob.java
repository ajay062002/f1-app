package com.example.f1app.service;

import com.example.f1app.model.AppUser;
import com.example.f1app.repository.AppUserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeeklyDigestJob {

    private final AppUserRepository users;
    private final EmailService emailService;

    public WeeklyDigestJob(AppUserRepository users, EmailService emailService) {
        this.users = users;
        this.emailService = emailService;
    }

    /**
     * Runs every Friday at 18:00 (6 PM) server time.
     * Cron format: second minute hour day-of-month month day-of-week
     * e.g. "0 0 18 * * FRI"
     *
     * For testing NOW, temporarily change to: "0 * * * * *" (every minute)
     */
    @Scheduled(cron = "0 0 18 * * FRI")
    public void sendWeeklyRaceDigest() {
        System.out.println("📢 WeeklyDigestJob started…");

        List<AppUser> all = users.findAll();
        int queued = 0;

        for (AppUser user : all) {
            String email = user.getEmail();
            if (email == null || email.isBlank()) continue;

            String name = user.getDisplayName() == null ? "friend" : user.getDisplayName();

            String subject = "🏁 Your F1 weekly update";
            String body = new StringBuilder()
                    .append("<h2>Hello ").append(name).append(" 👋</h2>")
                    .append("<p>Here’s your weekly F1 update from <b>Aj F1 Live</b>.</p>")
                    .append("<ul>")
                    .append("<li>We’ll send you reminders <b>before each race</b>.</li>")
                    .append("<li>Check drivers, teams, and standings any time.</li>")
                    .append("</ul>")
                    .append("<p style='margin-top:14px'>See the latest on the site:</p>")
                    .append("<p><a href='http://localhost:1947/index.html' ")
                    .append("style='display:inline-block;background:#ef4444;color:#fff;")
                    .append("padding:10px 16px;border-radius:10px;text-decoration:none;")
                    .append("font-weight:700'>Open Aj F1 Live</a></p>")
                    .toString();

            try {
                // async, non-blocking
                emailService.sendCustomEmail(email, subject, body);
                queued++;
            } catch (Exception ex) {
                System.err.println("❌ Failed to queue weekly email for: " + email);
                ex.printStackTrace();
            }
        }

        System.out.println("✅ WeeklyDigestJob queued emails for " + queued + " user(s).");
    }
}
