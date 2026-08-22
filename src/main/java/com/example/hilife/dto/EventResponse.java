package com.example.hilife.dto;

import com.example.hilife.entity.Event;
import com.example.hilife.entity.Gallery;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventResponse {
    private Event event;
    private List<Gallery> photos;
}
