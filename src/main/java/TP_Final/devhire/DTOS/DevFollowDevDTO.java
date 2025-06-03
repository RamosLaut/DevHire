package TP_Final.devhire.DTOS;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevFollowDevDTO {
    private Long followId;
    private Long devFollowerId;
    private Long devFollowedId;
}
