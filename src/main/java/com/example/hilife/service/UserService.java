package com.example.hilife.service;

import com.example.hilife.dto.LoginRequest;
import com.example.hilife.dto.LoginResponse;
import com.example.hilife.repository.UserRepository;
import com.example.hilife.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.hilife.entity.AppUser;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {

        AppUser user = userRepository
                .findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() ->
                        new RuntimeException("Phone number not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Password incorrect");
        }

        String token = jwtUtil.generateToken(
                user.getId(),
                user.getRole().name(),
                user.getFirstName()
        );

        return new LoginResponse(
                token,
                user.getId(),
                user.getFirstName(),
                user.getRole().name()
        );
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

}