package TP_Final.devhire.Model.Entities;

import TP_Final.devhire.Model.Enums.Level;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class AcademicInfo {
    private Long id;
    @NotEmpty
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Level level;

    private String degree;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AcademicInfo that)) return false;
        return Objects.equals(name, that.name) && level == that.level && Objects.equals(degree, that.degree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, degree);
    }
}
