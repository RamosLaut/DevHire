package TP_Final.devhire.Entities;

import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.Seniority;
import TP_Final.devhire.Enums.SoftSkills;
import jakarta.persistence.*;
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
    private String name;
    private String lastName;
    private String email;
    private String dni;
    private Seniority seniority;
    private List<JobExperienceEntity> jobExperience;
    private List<AcademicInfoEntity> academicInfo;
    private String location;
    private List<SoftSkills> softSkills;
    private List<HardSkills> hardSkills;
    private Boolean state;
}
