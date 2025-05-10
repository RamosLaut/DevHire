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
    private long user_id;
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
//    private List<JobExperienceEntity> jobExperience;
//    private List<AcademicInfoEntity> academicInfo;
    @NotNull
    private String location;
//    private List<SoftSkills> softSkills;
//    private List<HardSkills> hardSkills;
    @NotNull
    private Boolean state;
    @OneToMany(mappedBy = "userId")
    private List<PublicationEntity>publications;
    @OneToMany(mappedBy = "user_id")
    private List<LikeEntity>likes;
    @OneToMany(mappedBy = "user_id")
    private List<CommentEntity>comments;
}
