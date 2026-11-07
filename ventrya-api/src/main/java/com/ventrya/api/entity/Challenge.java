package com.ventrya.api.entity;

import com.ventrya.api.entity.enums.ProfessionalArea;
import com.ventrya.api.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "challenge")
public class Challenge {

    @OneToMany(mappedBy = "challenge")
    private Set<Application> applications = new HashSet<>();

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "difficulty")
    private String difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "professional_Area", nullable = false)
    private ProfessionalArea professionalArea;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "dead_Line", nullable = false)
    private String deadLine;

    @ManyToOne
    @JoinColumn(name = "organization_Id")
    private Organization organization;


}
