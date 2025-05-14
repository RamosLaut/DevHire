package TP_Final.devhire.Entities;

import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.Seniority;
import TP_Final.devhire.Enums.SoftSkills;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
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
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String lastName;
    @Email
    private String email;
    @NotEmpty
    private String dni;
    @Enumerated(EnumType.STRING)
    private Seniority seniority;
    @Column(unique = true, nullable = false)
    private String username;
    @NotEmpty
    private String password;

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
    @OneToMany(mappedBy = "user")
    private List<PublicationEntity>publications;
    @OneToMany(mappedBy = "user")
    private List<LikeEntity>likes;
    @OneToMany(mappedBy = "user")
    private List<CommentEntity>comments;

    @OneToMany(mappedBy = "follower")
    private List<FollowEntity> followed = new ArrayList<>();

    @OneToMany(mappedBy = "followed")
    private List<FollowEntity> followers = new ArrayList<>();

    @ManyToMany (mappedBy = "users")
    private Set<JobEntity> jobs;

}
