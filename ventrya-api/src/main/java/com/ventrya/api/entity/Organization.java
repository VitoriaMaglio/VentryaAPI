package com.ventrya.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "ventrya_organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_ventrya_organization")
    @SequenceGenerator(name = "seq_ventrya_organization", sequenceName = "SEQ_VENTRYA_ORGANIZATION", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email", unique = true,length = 255, nullable = false)
    private String email;

    @Column(name = "description",nullable = false,length = 2000)
    private String description;

    @Column(name = "web_Site")
    private String webSite;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "ventrya_organization_user",
            joinColumns = @JoinColumn(name = "organization_Id"),
            inverseJoinColumns = @JoinColumn(name = "user_Id")
    )
    private Set<User> representatives = new HashSet<>();

    @OneToMany(mappedBy = "organization")
    private Set<Challenge> challenges = new HashSet<>();



}
