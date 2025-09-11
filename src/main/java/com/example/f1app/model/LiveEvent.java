package com.example.f1app.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "live_events")
public class LiveEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false, length = 32)
    private String status = "OK";

    public LiveEvent() {
    }

    public LiveEvent(String message) {
        this.message = message;
        this.createdAt = Instant.now();
        this.status = "OK";
    }

    // getters / setters
    public Long getId() { return id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
