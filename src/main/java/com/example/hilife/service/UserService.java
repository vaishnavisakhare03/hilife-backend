package com.example.hilife.service;

import com.example.hilife.dto.LoginRequest;
import com.example.hilife.dto.LoginResponse;
import com.example.hilife.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.hilife.entity.AppUser;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {

        AppUser user = userRepository
                .findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() ->
                        new RuntimeException("Phone number not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Password incorrect");
        }

        return new LoginResponse(
                user.getId(),
                user.getFirstName(),
                user.getRole().name()
        );
    }

}