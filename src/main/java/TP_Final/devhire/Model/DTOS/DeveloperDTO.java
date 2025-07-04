package TP_Final.devhire.Model.DTOS;

import TP_Final.devhire.Model.Enums.HardSkills;
import TP_Final.devhire.Model.Enums.Seniority;
import TP_Final.devhire.Model.Enums.SoftSkills;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeveloperDTO {
private Long id;
private String name;
private String lastName;
private String dni;
private String location;
private Seniority seniority;
private List<SoftSkills> softSkills;
private List<HardSkills> hardSkills;
private List<AcademicInfoDTO> academicInfo;
private List<JobExperienceDTO> jobExperience;
private Boolean enabled;
private String email;
}
