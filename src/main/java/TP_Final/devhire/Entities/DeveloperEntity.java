package TP_Final.devhire.Entities;

import TP_Final.devhire.Entities.Follow.CompanyFollowsDeveloper;
import TP_Final.devhire.Entities.Follow.DeveloperFollowsCompany;
import TP_Final.devhire.Entities.Follow.DeveloperFollowsDeveloper;
import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.Seniority;
import TP_Final.devhire.Enums.SoftSkills;
import TP_Final.devhire.Security.Entities.CredentialsEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "devs")
public class DeveloperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Column(unique = true)
    private String dni;
    @Enumerated(EnumType.STRING)
    private Seniority seniority;
    @NotNull
    private Boolean enabled;
    @ElementCollection
    @CollectionTable(name = "dev_academic_info", joinColumns = @JoinColumn(name = "developer_id"))
    private List<AcademicInfo> academicInfo;
    @ElementCollection
    @CollectionTable(name = "dev_job_experience", joinColumns = @JoinColumn(name = "developer_id"))
    private List<JobExperience> jobExperience;
    @NotNull
    private String location;

    @ElementCollection(targetClass = SoftSkills.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "dev_soft_skills", joinColumns = @JoinColumn(name = "developer_id"))
    @Column(name = "skill")
    private List<SoftSkills> softSkills;

    @ElementCollection(targetClass = HardSkills.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "dev_hard_skills", joinColumns = @JoinColumn(name = "developer_id"))
    @Column(name = "skill")
    private List<HardSkills> hardSkills;

    @OneToMany(mappedBy = "developer")
    private List<PublicationEntity>publications;
    @OneToMany(mappedBy = "developer")
    private List<LikeEntity>likes;
    @OneToMany(mappedBy = "developer")
    private List<CommentEntity>comments;

    //Seguimientos:
    @OneToMany(mappedBy = "follower")
    private List<DeveloperFollowsDeveloper> developerFollowed = new ArrayList<>();

    @OneToMany(mappedBy = "followed")
    private List<DeveloperFollowsDeveloper> followings = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<DeveloperFollowsCompany> companiesFollowed = new ArrayList<>();

    @OneToMany(mappedBy = "followed")
    private List<CompanyFollowsDeveloper> companyFollowings = new ArrayList<>();

    //
    @OneToMany(mappedBy = "dev")
    private Set<ApplicationEntity> postulatedJobs;

    @OneToOne
    @JoinColumn(name = "user_id")
    private CredentialsEntity credentials;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperEntity developer = (DeveloperEntity) o;
        return Objects.equals(id, developer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
