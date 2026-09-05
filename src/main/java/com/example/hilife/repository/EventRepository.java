package com.example.hilife.repository;

import com.example.hilife.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Long> {
    long countByStartTimeAfter(LocalDateTime dateTime);
}
