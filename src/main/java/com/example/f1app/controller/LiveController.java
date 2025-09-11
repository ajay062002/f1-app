package com.example.f1app.controller;

import com.example.f1app.model.LiveEvent;
import com.example.f1app.repository.LiveEventRepository;
import com.example.f1app.service.LiveUpdateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/live")
@CrossOrigin
public class LiveController {

    private final LiveUpdateService live;
    private final LiveEventRepository repo;

    public LiveController(LiveUpdateService live, LiveEventRepository repo) {
        this.live = live;
        this.repo = repo;
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        return live.subscribe();
    }

    @PostMapping("/push")
    public void push(@RequestBody String message) {
        repo.save(new LiveEvent(message)); // needs LiveEvent(String) ctor
        live.push(message);
    }
}
