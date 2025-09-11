package com.example.f1app.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String from = "gmail"; // <-- your Gmail

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Welcome email after registration
     */
    @Async
    public void sendWelcomeEmail(String to, String name) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("🎉 Welcome to Aj F1 Live, " + name + "!");
            helper.setText(
                    "<h2>Hello " + name + " 👋</h2>"
                  + "<p>Welcome to <b>Aj F1 Live</b> 🏎️</p>"
                  + "<p>You’ll now receive updates before every race 🚦</p>"
                  + "<p>Stay tuned and enjoy the season!</p>",
                    true
            );

            mailSender.send(msg);
            System.out.println("✅ Welcome email sent to " + to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send welcome email to " + to);
            e.printStackTrace();
        }
    }

    /**
     * Generic custom email (used by WeeklyDigestJob or other features)
     */
    @Async
    public void sendCustomEmail(String to, String subject, String html) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(msg);
            System.out.println("✅ Custom email sent to " + to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send custom email to " + to);
            e.printStackTrace();
        }
    }
}
