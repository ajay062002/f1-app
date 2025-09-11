package com.example.f1app.service;

import com.example.f1app.controller.dto.RegisterRequest;
import com.example.f1app.model.User;
import com.example.f1app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getEmail()); // email stored in username
        user.setDisplayName(request.getDisplayName());
        user.setPassword(request.getPassword()); // later use hash
        return userRepository.save(user);
    }

    public User login(RegisterRequest request) {
        return userRepository.findByUsernameAndPassword(
                request.getEmail(), request.getPassword()
        ).orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}
