package TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow;

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
public class DeveloperFollowsCompanyId {
    private Long developerFollowerId;
    private Long companyFollowedId;
}
