package TP_Final.devhire.DTOS;

import TP_Final.devhire.Entities.AcademicInfo;
import TP_Final.devhire.Entities.JobExperience;
import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.Seniority;
import TP_Final.devhire.Enums.SoftSkills;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class UserRegisterDTO {
    @NotBlank
    private String name;
    @NotEmpty
    private String lastName;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String dni;
    @NotBlank
    private String username;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).{6,}$",
            message = "The password must have at least 6 characters, one capital letter and one number"
    )
    private String password;
    @NotBlank
    private String location;
    @NotNull
    private Seniority seniority;

    private List<SoftSkills> softSkills;

    private List<HardSkills> hardSkills;

    private List<AcademicInfo> academicInfo;

    private List<JobExperience> jobExperience;
}
