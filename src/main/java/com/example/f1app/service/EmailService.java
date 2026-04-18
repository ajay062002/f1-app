package com.example.f1app.service;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final String from = "gmail"; // set via MAIL_USER env var in application.properties

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
            helper.setSubject("Welcome to Aj F1 Live, " + name + "!");
            helper.setText(
                    "<h2>Hello " + name + "</h2>"
                  + "<p>Welcome to <b>Aj F1 Live</b>.</p>"
                  + "<p>You'll now receive updates before every race weekend.</p>"
                  + "<p>Stay tuned and enjoy the season!</p>",
                    true
            );
            mailSender.send(msg);
            log.info("Welcome email sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send welcome email to {}: {}", to, e.getMessage());
        }
    }

    /**
     * Generic custom email (used by WeeklyDigestJob and test endpoints)
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
            log.info("Email sent to {} with subject '{}'", to, subject);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }
}
