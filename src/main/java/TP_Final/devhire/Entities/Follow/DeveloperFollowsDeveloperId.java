package TP_Final.devhire.Entities.Follow;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode

@Embeddable
public class DeveloperFollowsDeveloperId implements Serializable {
    private Long developerFollowerId;
    private Long developerFollowedId;

}
