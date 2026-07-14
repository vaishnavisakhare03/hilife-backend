package com.example.hilife.entity;

        import jakarta.persistence.*;
        import lombok.Getter;
        import lombok.Setter;

        import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter
@Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "posted_by", length = 100)
    private String postedBy;

    @Column(name = "posted_by_user_id")
    private Long postedByUserId;

    @Column(name = "likes_count")
    private Integer likesCount = 0;

    @Column(name = "dislikes_count")
    private Integer dislikesCount = 0;

    @Column(name = "created_on", insertable = false, updatable = false)
    private LocalDateTime createdOn;
}