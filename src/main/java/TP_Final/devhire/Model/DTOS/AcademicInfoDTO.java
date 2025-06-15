package TP_Final.devhire.Model.DTOS;

import TP_Final.devhire.Model.Enums.Level;
import jakarta.validation.constraints.NotBlank;
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
