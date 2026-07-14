package com.example.hilife.controller;

import com.example.hilife.dto.ReactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.hilife.entity.Feedback;
import com.example.hilife.entity.Gallery;
import com.example.hilife.service.FeedbackService;
import com.example.hilife.dto.FeedbackResponse;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackService.createFeedback(feedback);
    }

    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    @GetMapping("/{id}")
    public Feedback getFeedback(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id);
    }

    @GetMapping("/{id}/with-photos")
    public FeedbackResponse getFeedbackWithPhotos(@PathVariable Long id) {
        return feedbackService.getFeedbackWithPhotos(id);
    }

    @PostMapping("/{feedbackId}/photos")
    public String addPhotos(
            @PathVariable Long feedbackId,
            @RequestBody List<Gallery> photos) {

        feedbackService.addPhotosToFeedback(feedbackId, photos);
        return "Photos added successfully";
    }

    @PostMapping("/{id}/like")
    public Feedback likeFeedback(
            @PathVariable Long id
    ) {
        return feedbackService.likeFeedback(id);
    }
    @PostMapping("/{id}/dislike")
    public Feedback dislikeFeedback(
            @PathVariable Long id
    ) {
        return feedbackService.dislikeFeedback(id);
    }
    @DeleteMapping("/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return "Feedback deleted successfully";
    }

    @PutMapping("/{id}")
    public Feedback updateFeedback(
            @PathVariable Long id,
            @RequestBody Feedback feedback
    ) {
        return feedbackService.updateFeedback(
                id,
                feedback
        );
    }
}
