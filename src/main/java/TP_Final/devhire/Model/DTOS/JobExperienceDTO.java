package TP_Final.devhire.Model.DTOS;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobExperienceDTO {
    @NotEmpty
    private String company;
    private String position;
    @NotNull(message = "Years of experience is required")
    @Min(value = 0, message = "Years must be non-negative")
    private Integer years;
}
