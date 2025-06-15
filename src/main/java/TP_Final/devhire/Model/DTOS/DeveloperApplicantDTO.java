package TP_Final.devhire.Model.DTOS;

import TP_Final.devhire.Model.Enums.Seniority;

import lombok.*;


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
