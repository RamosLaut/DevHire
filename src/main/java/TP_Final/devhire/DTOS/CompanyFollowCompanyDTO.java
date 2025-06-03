package TP_Final.devhire.DTOS;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyFollowCompanyDTO {
    private Long followId;
    private Long companyFollowerId;
    private Long companyFollowedId;
}
