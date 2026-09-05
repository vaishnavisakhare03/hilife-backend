package com.example.hilife.repository;

import com.example.hilife.entity.Task;
import com.example.hilife.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    long countByCurrentStatus(TaskStatus currentStatus);
}
