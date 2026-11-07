package com.ventrya.api.entity;

import com.ventrya.api.entity.enums.ProfessionalArea;
import com.ventrya.api.entity.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "challenge")
public class Challenge {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_challenge")
    @SequenceGenerator(name = "seq_challenge", sequenceName = "SEQ_CHALLENGE")
    private Long id;

    @OneToMany(mappedBy = "challenge")
    private Set<Application> applications = new HashSet<>();

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ChallengeStatus status;

    @Column(name = "requirements", length = 2000)
    private String requirements;

    @Enumerated(EnumType.STRING)
    @Column(name = "professional_Area", nullable = false)
    private ProfessionalArea professionalArea;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "dead_Line", nullable = false)
    private Date deadLine;

    @Column(name = "created_At", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "organization_Id")
    private Organization organization;


}
