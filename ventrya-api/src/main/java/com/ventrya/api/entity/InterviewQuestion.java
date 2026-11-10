package com.ventrya.api.entity;

import com.ventrya.api.entity.Interview;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        name = "ventrya_interview_question",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_interview_question_number",
                        columnNames = {"interview_id", "question_number"}
                )
        }
)
public class InterviewQuestion {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_ventrya_interview_question"
    )
    @SequenceGenerator(
            name = "seq_ventrya_interview_question",
            sequenceName = "seq_ventrya_interview_question",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @Column(name = "question_number", nullable = false)
    private Integer questionNumber;

    @Column(name = "question", nullable = false, length = 2000)
    private String question;

    @Column(name = "answer", length = 4000)
    private String answer;

    @Column(name = "ai_feedback", length = 4000)
    private String aiFeedback;
}
