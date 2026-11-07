package com.ventrya.api.entity;


import com.ventrya.api.entity.enums.PortfolioStatus;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "ventrya_portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_ventrya_portfolio"
    )
    @SequenceGenerator(
            name = "seq_ventrya_portfolio",
            sequenceName = "seq_ventrya_portfolio",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @OneToOne
    @JoinColumn(name = "submission_id", nullable = false, unique = true)
    private Submission submission;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "generated_description", length = 4000)
    private String generatedDescription;

    @Column(name = "skills", length = 2000)
    private String skills;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PortfolioStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}

