package com.example.hilife.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DashboardResponse {

    private long upcomingEvents;
    private long pendingTasks;
    private long feedbackCount;
    private long committeeCount;
}