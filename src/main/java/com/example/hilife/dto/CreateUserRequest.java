package com.example.hilife.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    private String firstName;
    private String middleName;
    private String lastName;

    private String phoneNumber;
    private String password;

    private String flatNumber;
    private String tower;

    private String role;
}