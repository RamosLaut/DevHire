package TP_Final.devhire.Model.DTOS;

import TP_Final.devhire.Model.Enums.FollowType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Representación de una relación de seguimiento")
public class FollowResponseDTO {
    @Schema(
            description = "Tipo de relación de seguimiento",
            example = "DEVELOPER_TO_COMPANY"
    )
    private FollowType type; //DEVELOPER_TO_DEVELOPER / COMPANY_TO_COMPANY / DEVELOPER_TO_COMPANY / COMPANY_TO_DEVELOPER

    @Schema(
            description = "ID del seguidor",
            example = "1"
    )
    private Long followerId;

    @Schema(
            description = "ID del seguido",
            example = "2"
    )
    private Long followedId;

    public FollowResponseDTO(FollowType type, long followerId, long followedId) {
        this.type = type;
        this.followerId = followerId;
        this.followedId = followedId;
    }
}
