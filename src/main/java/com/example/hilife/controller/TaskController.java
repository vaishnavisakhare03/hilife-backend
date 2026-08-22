package com.example.hilife.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.hilife.entity.Task;
import com.example.hilife.entity.Gallery;
import com.example.hilife.service.TaskService;
import com.example.hilife.dto.TaskResponse;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/{id}/with-photos")
    public TaskResponse getTaskWithPhotos(@PathVariable Long id) {
        return taskService.getTaskWithPhotos(id);
    }

    @PostMapping("/{taskId}/photos")
    @PreAuthorize("hasRole('ADMIN')")
    public String addPhotos(
            @PathVariable Long taskId,
            @RequestBody List<Gallery> photos) {

        taskService.addPhotosToTask(taskId, photos);
        return "Photos added successfully";
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task task
    ) {
        return taskService.updateTask(
                id,
                task
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "Task deleted successfully";
    }
}