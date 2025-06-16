package TP_Final.devhire.Model.DTOS;

import TP_Final.devhire.Model.Entities.AcademicInfo;
import TP_Final.devhire.Model.Entities.JobExperience;
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
public class DeveloperUpdateDTO {
        private String name;
        private String lastName;
        private String email;
        private String dni;
        private String password;
        private String location;
        private Seniority seniority;
        private List<SoftSkills> softSkills;
        private List<HardSkills> hardSkills;
        private List<AcademicInfo> academicInfo;
        private List<JobExperience> jobExperience;
}
