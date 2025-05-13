package TP_Final.devhire.Entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Embeddable
public class JobExperience {
    private Long Experience_id;
    @NotEmpty
    private String company;
    @NotNull
    private String position;
    @NotNull
    private Integer years;

}
