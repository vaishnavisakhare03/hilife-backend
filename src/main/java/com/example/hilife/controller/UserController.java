package com.example.hilife.controller;

import com.example.hilife.dto.*;
import com.example.hilife.entity.AppUser;
import com.example.hilife.service.OtpService;
import com.example.hilife.service.RegistrationTokenService;
import com.example.hilife.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final RegistrationTokenService registrationTokenService;
    private final OtpService otpService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AppUser createUser(
            @RequestBody CreateUserRequest request) {

        return userService.createUser(request);
    }

    @PostMapping("/register")
    public AppUser register(@RequestBody CreateUserRequest request) {
        return userService.registerUser(request);
    }

    public UserController(
            UserService userService,
            OtpService otpService,
            RegistrationTokenService registrationTokenService
    ) {
        this.userService = userService;
        this.otpService = otpService;
        this.registrationTokenService = registrationTokenService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @RequestBody UserResponse request) {

        return userService.updateUser(id, request);
    }

    @PutMapping("/{id}/change-password")
    public void changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request
    ) {
        userService.changePassword(id, request);
    }

    @PostMapping("/register/send-otp")
    public ResponseEntity<String> sendRegistrationOtp(
            @RequestBody SendOtpRequest request) {

        otpService.sendOtp(request.getPhoneNumber());

        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/register/verify-otp")
    public ResponseEntity<VerifyOtpResponse> verifyRegistrationOtp(
            @RequestBody VerifyOtpRequest request) {

        boolean verified = otpService.verifyOtp(
                request.getPhoneNumber(),
                request.getOtp()
        );

        if (!verified) {
            return ResponseEntity.ok(
                    new VerifyOtpResponse(false, null)
            );
        }

        String registrationToken =
                registrationTokenService.generateToken(
                        request.getPhoneNumber()
                );

        return ResponseEntity.ok(
                new VerifyOtpResponse(
                        true,
                        registrationToken
                )
        );
    }
}