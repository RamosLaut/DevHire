package TP_Final.devhire.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowResponseDTO {
    private String type; //DEVELOPER_TO_DEVELOPER / COMPANY_TO_COMPANY / DEVELOPER_TO_COMPANY / COMPANY_TO_DEVELOPER
    private Long followerId;
    private Long followedId;

    public FollowResponseDTO(String type, long followerId, long followedId) {
        this.type = type;
        this.followerId = followerId;
        this.followedId = followedId;
    }
}
