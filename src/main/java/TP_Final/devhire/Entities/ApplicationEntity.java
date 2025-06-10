package TP_Final.devhire.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "applicants")
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dev_id")
    private DeveloperEntity dev;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobEntity job;

    private LocalDateTime postulationDate = LocalDateTime.now();;
    private Boolean enabled = true;

}
