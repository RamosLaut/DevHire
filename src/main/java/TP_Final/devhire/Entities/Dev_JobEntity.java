package TP_Final.devhire.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "dev_job")
public class Dev_JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "dev_id")
    private DeveloperEntity dev;

    @Id
    @ManyToOne
    @JoinColumn(name = "job_id")

    private JobEntity Postulation_Job;
    private LocalDate Date_of_Postulation;
    private String State;

}
