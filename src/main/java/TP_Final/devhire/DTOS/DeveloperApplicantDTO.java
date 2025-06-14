package TP_Final.devhire.DTOS;

import TP_Final.devhire.Enums.Seniority;

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
