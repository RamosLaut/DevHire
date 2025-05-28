package TP_Final.devhire.Entities;

import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.SoftSkills;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "jobs")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String description;

    @NotEmpty
    private String location;

    @ElementCollection(targetClass = SoftSkills.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "job_soft_skills", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "skill")
    private List<SoftSkills> softSkills;

    @ElementCollection(targetClass = HardSkills.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "job_hard_skills", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "skill")
    private List<HardSkills> hardSkills;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @ManyToMany
    @JoinTable(name = "user_job", joinColumns = @JoinColumn(name = "job_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> users = new HashSet<>();

    @NotNull
    private Boolean state;
}
