package TP_Final.devhire.DTOS;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevFollowCompanyDTO {
    private Long followId;
    private Long devFollowerId;
    private Long companyFollowedId;
}
