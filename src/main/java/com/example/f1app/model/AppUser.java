package com.example.f1app.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "app_users",
       uniqueConstraints = @UniqueConstraint(name="UK_app_users_email", columnNames = "email"))
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email", nullable=false, length=160)
    private String email;

    @Column(name="display_name", length=120)
    private String displayName;

    @Column(name="password_hash", nullable=false, length=120)
    private String passwordHash;

    @Column(name="role", nullable=false, length=32)
    private String role = "USER";

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;

    // IMPORTANT: this must exist because the DB requires it
    @Column(name="updated_at", nullable=false)
    private Instant updatedAt;

    public AppUser() {}

    // ---- lifecycle hooks ----
    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.role == null) this.role = "USER";
        if (this.email != null) this.email = this.email.trim();
        if (this.displayName != null) this.displayName = this.displayName.trim();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    // ---- getters/setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
