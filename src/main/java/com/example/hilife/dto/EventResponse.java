package com.example.hilife.dto;

import com.example.hilife.entity.Event;
import com.example.hilife.entity.Gallery;
import java.util.List;

public class EventResponse {
    private Event event;
    private List<Gallery> photos;
    public void setEvent(Event event) {
        this.event = event;
    }
    public void setPhotos(List<Gallery> photos) {
        this.photos = photos;
    }
}
