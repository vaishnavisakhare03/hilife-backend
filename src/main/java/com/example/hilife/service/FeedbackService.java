package com.example.hilife.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.hilife.entity.Feedback;
import com.example.hilife.entity.Gallery;
import com.example.hilife.repository.FeedbackRepository;
import com.example.hilife.repository.GalleryRepository;
import com.example.hilife.dto.FeedbackResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final GalleryRepository galleryRepository;

    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }

    // Add photos
    public void addPhotosToFeedback(Long feedbackId, List<Gallery> photos) {

        for (Gallery photo : photos) {
            photo.setEntityType("FEEDBACK");
            photo.setParentEntityId(feedbackId);
        }

        galleryRepository.saveAll(photos);
    }

    // Fetch with photos
    public FeedbackResponse getFeedbackWithPhotos(Long id) {

        Feedback feedback = getFeedbackById(id);

        List<Gallery> photos =
                galleryRepository.findByEntityTypeAndParentEntityId("FEEDBACK", id);

        FeedbackResponse response = new FeedbackResponse();
        response.setFeedback(feedback);
        response.setPhotos(photos);

        return response;
    }
}
