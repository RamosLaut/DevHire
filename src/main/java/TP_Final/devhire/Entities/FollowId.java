package TP_Final.devhire.Entities;

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
public class FollowId implements Serializable {
    private Long follower_id;
    private Long followed_id;

}
