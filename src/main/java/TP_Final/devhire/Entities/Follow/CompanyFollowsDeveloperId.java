package TP_Final.devhire.Entities.Follow;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode

@Embeddable
public class CompanyFollowsDeveloperId {
    private Long companyFollowerId;
    private Long developerFollowedId;
}
