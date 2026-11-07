package com.ventrya.api.entity;
import jakarta.persistence.*;
import lombok.Data;
import com.ventrya.api.entity.enums.Status;
import java.time.LocalDateTime;

@Data
@Entity
@Table(
        name = "ventrya_application",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_application_student_challenge",
                columnNames = {"student_id", "challenge_id"}
        )
)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ventrya_application")
    @SequenceGenerator(name = "seq_ventrya_application", sequenceName = "SEQ_VENTRYA_APPLICATION", allocationSize = 1)
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
