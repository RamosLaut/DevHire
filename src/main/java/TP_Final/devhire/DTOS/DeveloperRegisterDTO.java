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
public class DeveloperRegisterDTO {
    @NotNull
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener mínimo 8 caracteres, almenos una letra mayúscula y un número")
    private String password;
    @NotBlank
    private String name;
    @NotEmpty
    private String lastName;
    @NotBlank
    private String dni;
    @NotBlank
    private String location;
}
