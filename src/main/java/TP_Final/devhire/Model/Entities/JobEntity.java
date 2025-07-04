package TP_Final.devhire.Model.Entities;

import TP_Final.devhire.Model.Enums.HardSkills;
import TP_Final.devhire.Model.Enums.SoftSkills;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    private String position;

    private String description;

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

    @OneToMany(mappedBy = "job")
    private Set<ApplicationEntity> applicants;

    @NotNull
    private Boolean state = true;
}
