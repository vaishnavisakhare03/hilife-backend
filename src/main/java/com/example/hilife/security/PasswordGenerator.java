package com.example.hilife.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();
        System.out.println("HIIIIIIIII");
        System.out.println(
                encoder.encode("Nagesh1234@")
        );

        System.out.println(
                encoder.encode("Vaishnavi@123")
        );

//        {
//            "firstName": "Rahul",
//                "middleName": "Kumar",
//                "lastName": "Sharma",
//                "phoneNumber": "9876543210",
//                "password": "Rahul@123",
//                "flatNumber": "A-101",
//                "tower": "A"
//        }
    }
}