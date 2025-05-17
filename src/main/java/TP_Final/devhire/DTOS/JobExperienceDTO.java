package TP_Final.devhire.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class JobExperienceDTO {
    @NotEmpty
    private String company;
    private String position;
    @NotBlank
    private Integer years;
}
