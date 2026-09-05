package com.example.hilife.controller;

import com.example.hilife.dto.DashboardResponse;
import com.example.hilife.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardResponse getDashboardStats() {
        return dashboardService.getDashboardStats();
    }
}