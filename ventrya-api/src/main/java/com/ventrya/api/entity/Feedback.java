package com.ventrya.api.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_feedback"
    )
    @SequenceGenerator(
            name = "seq_feedback",
            sequenceName = "seq_feedback",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "submission_id", nullable = false, unique = true)
    private Submission submission;

    @Column(name = "comment", nullable = false, length = 2000)
    private String comment;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "strengths", length = 2000)
    private String strengths;

    @Column(name = "improvements", length = 2000)
    private String improvements;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}

