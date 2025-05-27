package TP_Final.devhire.DTOS;

import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.Seniority;
import TP_Final.devhire.Enums.SoftSkills;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
private Long id;
private String name;
private String lastName;
private String email;
private String dni;
private String username;
private String location;
private Seniority seniority;
private List<SoftSkills> softSkills;
private List<HardSkills> hardSkills;
private List<AcademicInfoDTO> academicInfo;
private List<JobExperienceDTO> jobExperience;
private Boolean state;
}
