package com.ventrya.api.entity;
import com.ventrya.api.entity.enums.ProfessionalArea;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "interview")
public class Interview {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_interview"
    )
    @SequenceGenerator(
            name = "seq_interview",
            sequenceName = "seq_interview",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;//varias entrevistas possuem um registro de cada user

    @Enumerated(EnumType.STRING)
    @Column(name = "professional_area", nullable = false)
    private ProfessionalArea professionalArea;

    @Column(name = "question", nullable = false, length = 2000)
    private String question;

    @Column(name = "answer", length = 4000)
    private String answer;

    @Column(name = "ai_feedback", length = 4000)
    private String aiFeedback;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}

