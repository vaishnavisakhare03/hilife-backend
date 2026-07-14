package com.example.hilife.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "feedback_reaction",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "feedback_id",
                                "user_id"
                        }
                )
        }
)
@Getter
@Setter
public class FeedbackReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "feedback_id",
            nullable = false
    )
    private Feedback feedback;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType reactionType;
}