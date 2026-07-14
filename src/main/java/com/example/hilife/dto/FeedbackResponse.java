package com.example.hilife.dto;

import com.example.hilife.entity.Feedback;
import com.example.hilife.entity.Gallery;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class FeedbackResponse {


    private Feedback feedback;
    private List<Gallery> photos;

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public List<Gallery> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Gallery> photos) {
        this.photos = photos;
    }
}
