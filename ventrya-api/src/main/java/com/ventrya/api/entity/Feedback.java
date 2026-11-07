package com.ventrya.api.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ventrya_feedback")
public class Feedback {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_ventrya_feedback"
    )
    @SequenceGenerator(
            name = "seq_ventrya_feedback",
            sequenceName = "SEQ_VENTRYA_FEEDBACK",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "submission_id", nullable = false, unique = true)
    private Submission submission;

    @Column(name = "feedback_comment", nullable = false, length = 2000)
    private String comment;


    @Min(1)
    @Max(5)
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "strengths", length = 2000)
    private String strengths;

    @Column(name = "improvements", length = 2000)
    private String improvements;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}

