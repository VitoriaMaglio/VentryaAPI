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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")//Define que o id vai ser gerado automatico com uma sequence ->
    //sequence é um obj no banco oracle q fornece valores numéricos
    @SequenceGenerator(name = "seq_users", sequenceName = "SEQ_USERS", allocationSize = 1)//incrementa de 1 em 1
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email", unique = true,length = 225, nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "professional_Area", nullable = false)
    private ProfessionalArea professionalArea;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)//@Enumerated informa ao JPA que aquele atributo é um enum e define como seu valor será armazenado no banco.
    //.STRING salva com o valor do enum
    //.ORDINAL salva com numero da posição do valor
    @Column(name = "role", nullable = false)//validações nos dados
    private Role role;

    @ManyToMany(mappedBy = "representatives")//mappedBy representa o atributo Java da classe q define o relacionamento
    private Set<Organization> organizations = new HashSet<>();//aqui também precisamos de uma coleção para representar as organizações q um user pode ter

    @OneToMany(mappedBy = "students")
    private Set<Application> applications = new HashSet<>();
    /*
@OneToOne
@OneToMany
@ManyToOne
@ManyToMany -> requires a tabela intermediária que possui os dois ids da duas tabelas no relacionamento
     */









}
