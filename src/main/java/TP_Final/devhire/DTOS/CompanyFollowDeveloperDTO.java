package TP_Final.devhire.DTOS;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyFollowDeveloperDTO {
    private Long followId;
    private Long companyFollowerId;
    private Long devFollowedId;
}
