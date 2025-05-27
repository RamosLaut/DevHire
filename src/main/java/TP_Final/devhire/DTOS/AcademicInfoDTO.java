package TP_Final.devhire.DTOS;

import TP_Final.devhire.Enums.Level;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AcademicInfoDTO {
    @NotBlank
    private String name;
    @NotNull
    private Level level;
    private String degree;
}
