package TP_Final.devhire.DTOS;

import TP_Final.devhire.Entities.AcademicInfo;
import TP_Final.devhire.Entities.JobExperience;
import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.Seniority;
import TP_Final.devhire.Enums.SoftSkills;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
private Long user_id;
private String name;
private String lastName;
private String email;
private String dni;
private String username;
private String location;
private Seniority seniority;
private List<SoftSkills> softSkills;
private List<HardSkills> hardSkills;
private List<AcademicInfo> academicInfo;
private List<JobExperience> jobExperience;
}
