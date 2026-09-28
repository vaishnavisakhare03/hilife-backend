package com.example.hilife.service;

import com.example.hilife.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RegistrationTokenService {

    private final JwtUtil jwtUtil;

    private static final long REGISTRATION_TOKEN_EXPIRATION =
            10 * 60 * 1000; // 10 minutes

    public String generateToken(String phoneNumber) {

        return Jwts.builder()
                .claim("phoneNumber", phoneNumber)
                .claim("purpose", "REGISTRATION")
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + REGISTRATION_TOKEN_EXPIRATION
                        )
                )
                .signWith(jwtUtil.getKey())
                .compact();
    }

    public String validateToken(String token) {

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtUtil.getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String purpose = claims.get("purpose", String.class);

            if (!"REGISTRATION".equals(purpose)) {
                return null;
            }

            return claims.get("phoneNumber", String.class);

        } catch (Exception e) {
            return null;
        }
    }
}