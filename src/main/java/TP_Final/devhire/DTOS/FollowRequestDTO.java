package TP_Final.devhire.DTOS;

import TP_Final.devhire.Entities.Follow.DeveloperFollowsDeveloperId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Datos requeridos para crear o modificar una relación de seguimiento")
public class FollowRequestDTO {

   @Schema(description = "ID del seguidor (usuario o empresa)", example = "1")
   private Long followerId;

   @Schema(description = "Tipo del seguidor: DEVELOPER o COMPANY", example = "DEVELOPER")
   private String followerType; //DEVELOPER - COMPANY

   @NotNull
   @Schema(description = "ID del seguido (usuario o empresa)", example = "2", required = true)
   private Long followedId;

   @NotNull
   @Schema(description = "Tipo del seguido: DEVELOPER o COMPANY", example = "COMPANY", required = true)
   private String followedType; //DEVELOPER - COMPANY

   public FollowRequestDTO() {
   }

   public FollowRequestDTO(long followerId, String followerType, long followedId, String followedType) {
      this.followerId = followerId;
      this.followerType = followerType;
      this.followedId = followedId;
      this.followedType = followedType;
   }
}
