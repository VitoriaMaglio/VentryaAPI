package com.ventrya.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_organization")
    @SequenceGenerator(name = "seq_organization", sequenceName = "SEQ_ORGANIZATION", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email", unique = true,length = 225, nullable = false)
    private String email;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "web_Site",nullable = false)
    private String webSite;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    //como vamos representar cários usuários q podem estar atreladados a uma organização precisamos d euma colection
    //Set pois é uma coleção que n permite usuarios duplicados diferente de List
    @ManyToMany
    @JoinTable(//representa a tabela intermediária
            name = "organization_users",
            joinColumns = @JoinColumn(name = "organization_Id"),//representa a chave estrangeira
            inverseJoinColumns = @JoinColumn(name = "user_Id")//representa a chave estrangeira
    )
    private Set<User> representatives = new HashSet<>();//new HashSet inicia a coleção

    @OneToMany(mappedBy = "organization")
    private Set<Challenge> challenges = new HashSet<>();



}
