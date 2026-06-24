package com.example.hilife.service;

import com.example.hilife.dto.EventResponse;
import com.example.hilife.entity.Gallery;
import com.example.hilife.repository.GalleryRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

import com.example.hilife.entity.Event;
import com.example.hilife.repository.EventRepository;
import com.example.hilife.exception.EventNotFoundException;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final GalleryRepository galleryRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
    }

    public void addPhotosToEvent(Long eventId, List<Gallery> photos) {

        for (Gallery photo : photos) {
            photo.setEntityType("EVENT");
            photo.setParentEntityId(eventId);
        }

        galleryRepository.saveAll(photos);
    }

    public EventResponse getEventWithPhotos(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<Gallery> photos =
                galleryRepository.findByEntityTypeAndParentEntityId("EVENT", id);

        EventResponse response = new EventResponse();
        response.setEvent(event);
        response.setPhotos(photos);

        return response;
    }


    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }
}
