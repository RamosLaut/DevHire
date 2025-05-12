package TP_Final.devhire.Entities;

import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.Seniority;
import TP_Final.devhire.Enums.SoftSkills;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @Email
    private String email;
    @NotNull
    private String dni;
    @Enumerated(EnumType.STRING)
    private Seniority seniority;

    @ElementCollection
    @CollectionTable(name = "user_academic_info", joinColumns = @JoinColumn(name = "user_id"))
    private List<AcademicInfo> academicInfo;

    @ElementCollection
    @CollectionTable(name = "user_job_experience", joinColumns = @JoinColumn(name = "user_id"))
    private List<JobExperience> jobExperience;

    @NotNull
    private String location;

    @ElementCollection(targetClass = SoftSkills.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_soft_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skill")
    private List<SoftSkills> softSkills;

    @ElementCollection(targetClass = HardSkills.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_hard_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skill")
    private List<HardSkills> hardSkills;

    @NotNull
    private Boolean state;
    @OneToMany(mappedBy = "userId")
    private List<PublicationEntity>publications;
    @OneToMany(mappedBy = "user_id")
    private List<LikeEntity>likes;
    @OneToMany(mappedBy = "user_id")
    private List<CommentEntity>comments;
}
