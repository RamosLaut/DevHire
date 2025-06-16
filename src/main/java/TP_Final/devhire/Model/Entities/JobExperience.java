package TP_Final.devhire.Model.Entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Embeddable
public class JobExperience {
    private Long id;
    @NotEmpty
    private String company;
    @NotNull
    private String position;
    @NotNull
    private Integer years;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JobExperience that)) return false;
        return Objects.equals(company, that.company) && Objects.equals(position, that.position) && Objects.equals(years, that.years);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, position, years);
    }
}
