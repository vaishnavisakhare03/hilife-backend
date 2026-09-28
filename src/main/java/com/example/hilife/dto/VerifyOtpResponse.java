package com.example.hilife.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerifyOtpResponse {

    private boolean verified;
    private String registrationToken;
}