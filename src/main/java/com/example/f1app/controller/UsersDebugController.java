package com.example.f1app.controller;

import com.example.f1app.model.AppUser;
import com.example.f1app.repository.AppUserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/debug")
public class UsersDebugController {

    private final AppUserRepository repo;

    public UsersDebugController(AppUserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/users")
    public List<Map<String, Object>> list() {
        List<AppUser> all = repo.findAll();
        List<Map<String, Object>> out = new ArrayList<>();
        for (AppUser u : all) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", u.getId());
            m.put("email", u.getEmail());
            m.put("displayName", (u.getDisplayName() != null && !u.getDisplayName().isBlank())
                    ? u.getDisplayName() : u.getEmail());
            m.put("role", u.getRole());
            m.put("createdAt", u.getCreatedAt());
            m.put("updatedAt", u.getUpdatedAt());
            out.add(m);
        }
        return out;
    }
}
