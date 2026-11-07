package com.ventrya.api.entity;


import com.ventrya.api.entity.enums.SubmissionStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_submission"
    )
    @SequenceGenerator(
            name = "seq_submission",
            sequenceName = "seq_submission",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "application_id", nullable = false, unique = true)
    private Application application;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "file_url")
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubmissionStatus status;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @OneToOne(mappedBy = "submission")
    private Feedback feedback;
}

