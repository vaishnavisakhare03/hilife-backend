package com.example.hilife.repository;

import java.util.Optional;

import com.example.hilife.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    // Used during login (JWT authentication)
    Optional<AppUser> findByPhoneNumber(String phoneNumber);

    // Used during registration validation
    boolean existsByPhoneNumber(String phoneNumber);
}
