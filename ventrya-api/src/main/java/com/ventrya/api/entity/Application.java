package com.ventrya.api.entity;

import io.micrometer.common.lang.NonNullFields;
import jakarta.persistence.*;
import lombok.Data;
import okhttp3.Challenge;
import com.ventrya.api.entity.enums.Status;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_application")
    @SequenceGenerator(name = "seq_application", sequenceName = "SEQ_APPLICATION", allocationSize = 1)
    private Long id;

    @ManyToOne//várias applications podem pertencer ao mesmo user
    @JoinColumn(name = "student_Id")// joinColumn representa a fk, essa classe q vai guardar o id do estudante pois é o lado q domina o relacionamento
    private User students;

    @ManyToOne
    @JoinColumn(name = "challenge_Id")
    private Challenge challenge;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "applied_At")
    private LocalDateTime appliedAt;


}
