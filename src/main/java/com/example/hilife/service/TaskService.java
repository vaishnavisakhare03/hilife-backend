package com.example.hilife.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.hilife.entity.Task;
import com.example.hilife.entity.Gallery;
import com.example.hilife.repository.TaskRepository;
import com.example.hilife.repository.GalleryRepository;
import com.example.hilife.dto.TaskResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final GalleryRepository galleryRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Add photos to task
    public void addPhotosToTask(Long taskId, List<Gallery> photos) {

        for (Gallery photo : photos) {
            photo.setEntityType("TASK");
            photo.setParentEntityId(taskId);
        }

        galleryRepository.saveAll(photos);
    }

    // Get task with photos
    public TaskResponse getTaskWithPhotos(Long id) {

        Task task = getTaskById(id);

        List<Gallery> photos =
                galleryRepository.findByEntityTypeAndParentEntityId("TASK", id);

        TaskResponse response = new TaskResponse();
        response.setTask(task);
        response.setPhotos(photos);

        return response;
    }
}
