package com.example.hilife.service;

import com.example.hilife.dto.ChangePasswordRequest;
import com.example.hilife.dto.LoginRequest;
import com.example.hilife.dto.LoginResponse;
import com.example.hilife.dto.UserResponse;
import com.example.hilife.entity.Gallery;
import com.example.hilife.entity.Role;
import com.example.hilife.repository.GalleryRepository;
import com.example.hilife.repository.UserRepository;
import com.example.hilife.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.hilife.entity.AppUser;
import com.example.hilife.dto.CreateUserRequest;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final GalleryRepository galleryRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUser createUser(CreateUserRequest request) {

        if (userRepository.existsByPhoneNumber(
                request.getPhoneNumber())) {

            throw new RuntimeException(
                    "Phone number already registered");
        }

        AppUser user = new AppUser();

        user.setFirstName(request.getFirstName());
        user.setMiddleName(request.getMiddleName());
        user.setLastName(request.getLastName());

        user.setPhoneNumber(request.getPhoneNumber());

        user.setFlatNumber(request.getFlatNumber());
        user.setTower(request.getTower());

        user.setRole(
                Role.valueOf(request.getRole().toUpperCase())
        );

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        AppUser user = userRepository
                .findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() ->
                        new RuntimeException("Phone number not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Password incorrect");
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

    public UserResponse getUserById(Long id) {

            AppUser user = userRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("User not found"));

            UserResponse response = new UserResponse();

            response.setId(user.getId());
            response.setFirstName(user.getFirstName());
            response.setMiddleName(user.getMiddleName());
            response.setLastName(user.getLastName());
            response.setPhoneNumber(user.getPhoneNumber());
            response.setFlatNumber(user.getFlatNumber());
            response.setTower(user.getTower());
            response.setRole(user.getRole());

            return response;
        }

    public UserResponse updateUser(Long id, UserResponse request) {

        AppUser user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setMiddleName(request.getMiddleName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setTower(request.getTower());
        user.setFlatNumber(request.getFlatNumber());

        if (request.getPhotoId() != null) {

            Gallery photo = galleryRepository.findById(request.getPhotoId())
                    .orElseThrow(() -> new RuntimeException("Photo not found"));

            user.setPhoto(photo);
        }

        userRepository.save(user);

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setMiddleName(user.getMiddleName());
        response.setLastName(user.getLastName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setTower(user.getTower());
        response.setFlatNumber(user.getFlatNumber());
        response.setRole(user.getRole());

        if (user.getPhoto() != null) {
            response.setPhotoUrl(user.getPhoto().getImageUrl());
        }

        return response;
    }

    public void changePassword(
            Long userId,
            ChangePasswordRequest request
    ) {

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Current password is incorrect");
        }

        String passwordRegex =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";

        if (!request.getNewPassword()
                .matches(passwordRegex)) {

            throw new RuntimeException(
                    "Password must contain minimum 8 characters, "
                            + "one uppercase, one lowercase, "
                            + "one number and one special character");
        }

        if (request.getCurrentPassword()
                .equals(request.getNewPassword())) {

            throw new RuntimeException(
                    "New password cannot be same as current password");
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);
    }
}

