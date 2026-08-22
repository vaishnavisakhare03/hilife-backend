package com.example.hilife.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.hilife.entity.Gallery;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

import com.example.hilife.dto.EventResponse;

import com.example.hilife.entity.Event;
import com.example.hilife.service.EventService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping
    public List<EventResponse> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping("/{eventId}/photos")
    @PreAuthorize("hasRole('ADMIN')")
    public String addPhotos(
            @PathVariable Long eventId,
            @RequestBody List<Gallery> photos) {

        eventService.addPhotosToEvent(eventId, photos);
        return "Photos added successfully";
    }

    @GetMapping("/{id}/with-photos")
    public EventResponse getEventWithPhotos(@PathVariable Long id) {
        return eventService.getEventWithPhotos(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Event updateEvent(
            @PathVariable Long id,
            @RequestBody Event event
    ) {
        return eventService.updateEvent(id, event);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEvent(@PathVariable Long id) {

        eventService.deleteEvent(id);

        return "Event deleted successfully";
    }

}
