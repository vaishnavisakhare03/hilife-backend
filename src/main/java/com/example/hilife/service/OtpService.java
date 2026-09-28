package com.example.hilife.service;

import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Value("${twilio.verify-service-sid}")
    private String serviceSid;

    @Value("${otp.provider:twilio}")
    private String otpProvider;

    @Value("${otp.dev-code:123456}")
    private String devCode;

    public void sendOtp(String phoneNumber) {

        String phone = normalizePhoneNumber(phoneNumber);

        // Development OTP
        if ("dev".equalsIgnoreCase(otpProvider)) {
            System.out.println(
                    "DEV OTP for " + phone + " = " + devCode
            );
            return;
        }

        // Twilio OTP
        Verification verification = Verification.creator(
                serviceSid,
                phone,
                "sms"
        ).create();

        if (!"pending".equalsIgnoreCase(
                verification.getStatus())) {

            throw new RuntimeException("Failed to send OTP");
        }
    }

    public boolean verifyOtp(
            String phoneNumber,
            String otp
    ) {

        String phone = normalizePhoneNumber(phoneNumber);

        // Development OTP
        if ("dev".equalsIgnoreCase(otpProvider)) {
            return devCode.equals(otp);
        }

        // Twilio OTP
        VerificationCheck verificationCheck =
                VerificationCheck.creator(serviceSid)
                        .setTo(phone)
                        .setCode(otp)
                        .create();

        return "approved".equalsIgnoreCase(
                verificationCheck.getStatus()
        );
    }

    private String normalizePhoneNumber(
            String phoneNumber
    ) {

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new RuntimeException(
                    "Phone number is required"
            );
        }

        String phone = phoneNumber.trim();

        if (phone.startsWith("+91")) {
            return phone;
        }

        if (phone.matches("\\d{10}")) {
            return "+91" + phone;
        }

        throw new RuntimeException(
                "Invalid phone number. Enter a valid 10-digit Indian phone number."
        );
    }
}