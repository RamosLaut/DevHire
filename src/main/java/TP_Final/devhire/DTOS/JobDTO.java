package TP_Final.devhire.DTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JobDTO {
    Long id;
    @NotBlank(message = "Position cannot be empty")
    String position;
    @NotBlank(message = "Description cannot be empty")
    String description;
    @NotBlank(message = "Location cannot be empty")
    String location;
    String companyName;
}
