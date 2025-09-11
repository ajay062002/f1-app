package com.example.f1app.controller;

import com.example.f1app.controller.dto.ChatRequest;
import com.example.f1app.controller.dto.ChatResponse;
import com.example.f1app.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/ask")
    public ChatResponse ask(@RequestBody ChatRequest req) {
        return chatService.answer(req.getMessage());
    }
}
