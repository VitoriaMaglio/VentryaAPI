package com.ventrya.api.entity;
import com.ventrya.api.entity.enums.InterviewStatus;
import com.ventrya.api.entity.enums.ProfessionalArea;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ventrya_interview")
public class Interview {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_ventrya_interview"
    )
    @SequenceGenerator(
            name = "seq_ventrya_interview",
            sequenceName = "seq_ventrya_interview",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Enumerated(EnumType.STRING)
    @Column(name = "professional_area", nullable = false)
    private ProfessionalArea professionalArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InterviewStatus status;

    @Column(name = "overall_feedback", length = 4000)
    private String overallFeedback;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @OneToMany(
            mappedBy = "interview",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("questionNumber ASC")
    private List<InterviewQuestion> questions = new ArrayList<>();
}

