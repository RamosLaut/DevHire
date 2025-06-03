package TP_Final.devhire.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowResponseDTO {
    private String type; //DEVELOPER_TO_DEVELOPER / COMPANY_TO_COMPANY / DEVELOPER_TO_COMPANY / COMPANY_TO_DEVELOPER
    private long followerID;
    private long followedID;

    public FollowResponseDTO(String type, long followerID, long followedID) {
        this.type = type;
        this.followerID = followerID;
        this.followedID = followedID;
    }
}
