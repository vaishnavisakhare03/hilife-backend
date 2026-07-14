package com.example.hilife.repository;

import com.example.hilife.entity.FeedbackReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface FeedbackReactionRepository
        extends JpaRepository<FeedbackReaction, Long> {

    Optional<FeedbackReaction>
    findByFeedback_IdAndUserId(
            Long feedbackId,
            Long userId
    );


    @Transactional
    void deleteAllByFeedback_Id(Long feedbackId);
}