package com.example.hilife.service;

import com.example.hilife.security.JwtUtil;
import com.example.hilife.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.hilife.entity.Feedback;
import com.example.hilife.entity.Gallery;
import com.example.hilife.repository.FeedbackRepository;
import com.example.hilife.repository.GalleryRepository;
import com.example.hilife.dto.FeedbackResponse;

import com.example.hilife.entity.FeedbackReaction;
import com.example.hilife.entity.ReactionType;
import com.example.hilife.repository.FeedbackReactionRepository;

import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final GalleryRepository galleryRepository;
    private final FeedbackReactionRepository feedbackReactionRepository;
    private final JwtUtil jwtUtil;

    public Feedback createFeedback(Feedback feedback) {

        Long currentUserId =
                SecurityUtils.getCurrentUserId();

        feedback.setPostedByUserId(
                SecurityUtils.getCurrentUserId()
        );

        feedback.setPostedBy(
                SecurityUtils.getCurrentUserFirstName()
        );

        feedback.setLikesCount(0);
        feedback.setDislikesCount(0);

        return feedbackRepository.save(
                feedback
        );
    }
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll(
                Sort.by(
                        Sort.Direction.DESC,
                        "createdOn"
                )
        );
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
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

    public Feedback updateFeedback(
            Long feedbackId,
            Feedback updatedFeedback
    ) {

        Feedback existingFeedback =
                feedbackRepository.findById(feedbackId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Feedback not found"
                                ));

        Long currentUserId =
                SecurityUtils.getCurrentUserId();

        if (!existingFeedback.getPostedByUserId()
                .equals(currentUserId)) {

            throw new RuntimeException(
                    "You can only edit your own feedback"
            );
        }

        existingFeedback.setTitle(
                updatedFeedback.getTitle()
        );

        existingFeedback.setDescription(
                updatedFeedback.getDescription()
        );

        return feedbackRepository.save(
                existingFeedback
        );
    }

    public Feedback likeFeedback(
            Long feedbackId
    ){

        Long userId = SecurityUtils.getCurrentUserId();

        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() ->
                        new RuntimeException("Feedback not found"));

        Optional<FeedbackReaction> existingReaction =
                feedbackReactionRepository
                        .findByFeedback_IdAndUserId(
                                feedbackId,
                                userId
                        );


        if (existingReaction.isPresent()) {

            FeedbackReaction reaction = existingReaction.get();

            // User already liked
            if (reaction.getReactionType() == ReactionType.LIKE) {
                return feedback;
            }

            // User disliked earlier -> switch to like
            feedback.setDislikesCount(
                    feedback.getDislikesCount() - 1
            );

            feedback.setLikesCount(
                    feedback.getLikesCount() + 1
            );

            reaction.setReactionType(
                    ReactionType.LIKE
            );

            feedbackReactionRepository.save(reaction);

        } else {

            FeedbackReaction reaction = new FeedbackReaction();

            reaction.setFeedback(feedback);
            reaction.setUserId(userId);
            reaction.setReactionType(
                    ReactionType.LIKE
            );

            feedbackReactionRepository.save(reaction);

            feedback.setLikesCount(
                    feedback.getLikesCount() + 1
            );
        }

        return feedbackRepository.save(feedback);
    }

    public Feedback dislikeFeedback(Long feedbackId) {

        Long userId = SecurityUtils.getCurrentUserId();

        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() ->
                        new RuntimeException("Feedback not found"));

        Optional<FeedbackReaction> existingReaction =
                feedbackReactionRepository
                        .findByFeedback_IdAndUserId(
                                feedbackId,
                                userId
                        );

        if (existingReaction.isPresent()) {

            FeedbackReaction reaction = existingReaction.get();

            // User already disliked
            if (reaction.getReactionType() == ReactionType.DISLIKE) {
                return feedback;
            }

            // User liked earlier -> switch to dislike
            feedback.setLikesCount(
                    feedback.getLikesCount() - 1
            );

            feedback.setDislikesCount(
                    feedback.getDislikesCount() + 1
            );

            reaction.setReactionType(
                    ReactionType.DISLIKE
            );

            feedbackReactionRepository.save(reaction);

        } else {

            FeedbackReaction reaction = new FeedbackReaction();

            reaction.setFeedback(feedback);
            reaction.setUserId(userId);
            reaction.setReactionType(
                    ReactionType.DISLIKE
            );

            feedbackReactionRepository.save(reaction);

            feedback.setDislikesCount(
                    feedback.getDislikesCount() + 1
            );
        }

        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long feedbackId) {

        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() ->
                        new RuntimeException("Feedback not found"));

        Long currentUserId =
                SecurityUtils.getCurrentUserId();

        String currentUserRole =
                SecurityUtils.getCurrentUserRole();

        boolean isOwner =
                feedback.getPostedByUserId()
                        .equals(currentUserId);

        boolean isAdmin =
                currentUserRole.equals("ROLE_ADMIN");

        if (!isOwner && !isAdmin) {
            throw new RuntimeException(
                    "You are not authorized to delete this feedback"
            );
        }

        feedbackReactionRepository
                .deleteAllByFeedback_Id(feedbackId);

        feedbackRepository.delete(feedback);
    }
}
