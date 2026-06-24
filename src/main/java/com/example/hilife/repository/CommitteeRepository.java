package com.example.hilife.repository;

import com.example.hilife.entity.Committee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitteeRepository
        extends JpaRepository<Committee, Long> {
}