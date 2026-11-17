package com.ventrya.api.entity;


import com.ventrya.api.entity.enums.ProfessionalArea;
import jakarta.persistence.*;
import lombok.Data;
import com.ventrya.api.entity.enums.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "ventrya_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ventrya_users")//Define que o id vai ser gerado automatico com uma sequence ->

    @SequenceGenerator(name = "seq_ventrya_users", sequenceName = "SEQ_VENTRYA_USERS", allocationSize = 1)//incrementa de 1 em 1
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email", unique = true,length = 255, nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "professional_Area", nullable = false)
    private ProfessionalArea professionalArea;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)

    @Column(name = "role", nullable = false)
    private Role role;

    @ManyToMany(mappedBy = "representatives")
    private Set<Organization> organizations = new HashSet<>();

    @OneToMany(mappedBy = "students")
    private Set<Application> applications = new HashSet<>();










}
