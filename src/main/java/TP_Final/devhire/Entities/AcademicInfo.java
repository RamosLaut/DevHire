package TP_Final.devhire.Entities;

import TP_Final.devhire.Enums.Level;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class AcademicInfo {
    private Long AcademicInfo_id;
    @NotEmpty
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Level level;

    private String degree;
}
