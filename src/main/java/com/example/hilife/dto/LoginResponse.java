package com.example.hilife.dto;

public class LoginResponse {

    private String token;

    private Long id;
    private String firstName;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(
            String token,
            Long id,
            String firstName,
            String role
    ) {
        this.token = token;
        this.id = id;
        this.firstName = firstName;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}