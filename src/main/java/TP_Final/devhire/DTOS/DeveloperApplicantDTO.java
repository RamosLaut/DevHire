package TP_Final.devhire.DTOS;

import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.Seniority;
import TP_Final.devhire.Enums.SoftSkills;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperApplicantDTO {
    private String name;
    private String lastName;
    private String location;
    private Seniority seniority;
    private String email;
}
