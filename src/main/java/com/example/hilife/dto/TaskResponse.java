package com.example.hilife.dto;

import com.example.hilife.entity.Task;
import com.example.hilife.entity.Gallery;

import java.util.List;

public class TaskResponse {

    private Task task;
    private List<Gallery> photos;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<Gallery> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Gallery> photos) {
        this.photos = photos;
    }
}
