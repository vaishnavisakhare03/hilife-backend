package com.example.hilife.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getCurrentUserId() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if (authentication == null ||
                authentication.getPrincipal() == null) {
            throw new RuntimeException("User not authenticated");
        }

        return (Long) authentication.getPrincipal();
    }

    public static String getCurrentUserRole() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        return authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();
    }

    public static String getCurrentUserFirstName() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        return (String) authentication.getDetails();
    }

}