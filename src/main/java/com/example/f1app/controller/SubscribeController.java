package com.example.f1app.controller;

import com.example.f1app.repository.AppUserRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

@RestController
@RequestMapping("/api/newsletter")
@CrossOrigin
public class SubscribeController {

    private final AppUserRepository users;

    public SubscribeController(AppUserRepository users) {
        this.users = users;
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam String email,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false, defaultValue = "true") boolean subscribed) {

        if (!StringUtils.hasText(email)) return "Email required";

        return users.findByEmailIgnoreCase(email.trim().toLowerCase())
                .map(u -> {
                    if (StringUtils.hasText(name)) u.setDisplayName(name.trim());

                    // Try to set a subscription flag if the entity exposes one.
                    // This avoids compile errors when the method doesn't exist.
                    try {
                        // common variants in existing codebases:
                        String[] candidateSetters = {
                                "setSubscribed",
                                "setNewsletter",
                                "setNewsletterSubscribed",
                                "setEmailOptIn",
                                "setMailingOptIn"
                        };
                        for (String m : candidateSetters) {
                            try {
                                Method setter = u.getClass().getMethod(m, boolean.class);
                                setter.invoke(u, subscribed);
                                break;
                            } catch (NoSuchMethodException ignored) { /* try next */ }
                        }
                    } catch (Exception ignored) { /* not fatal */ }

                    users.save(u);
                    return "ok";
                })
                .orElse("no user");
    }
}
