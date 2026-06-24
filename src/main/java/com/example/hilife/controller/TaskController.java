package com.example.hilife.controller;

import lombok.RequiredArgsConstructor;
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
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
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
    public String addPhotos(
            @PathVariable Long taskId,
            @RequestBody List<Gallery> photos) {

        taskService.addPhotosToTask(taskId, photos);
        return "Photos added successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "Task deleted successfully";
    }
}
