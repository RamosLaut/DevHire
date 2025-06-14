package TP_Final.devhire.DTOS;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class JobExperienceDTO {
    @NotEmpty
    private String company;
    private String position;
    @NotNull(message = "Years of experience is required")
    @Min(value = 0, message = "Years must be non-negative")
    private Integer years;
}
