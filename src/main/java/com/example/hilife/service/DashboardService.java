package com.example.hilife.service;

import com.example.hilife.dto.DashboardResponse;
import com.example.hilife.entity.TaskStatus;
import com.example.hilife.repository.EventRepository;
import com.example.hilife.repository.TaskRepository;
import com.example.hilife.repository.FeedbackRepository;
import com.example.hilife.repository.CommitteeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EventRepository eventRepository;
    private final TaskRepository taskRepository;
    private final FeedbackRepository feedbackRepository;
    private final CommitteeRepository committeeRepository;

    public DashboardResponse getDashboardStats() {

        long upcomingEvents =
                eventRepository.countByStartTimeAfter(LocalDateTime.now());

        long pendingTasks =
                taskRepository.countByCurrentStatus(TaskStatus.valueOf("IN_PROGRESS"));

        long feedbackCount =
                feedbackRepository.count();

        long committeeCount =
                committeeRepository.count();

        return new DashboardResponse(
                upcomingEvents,
                pendingTasks,
                feedbackCount,
                committeeCount
        );
    }
}