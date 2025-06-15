package TP_Final.devhire.Model.DTOS;


import TP_Final.devhire.Model.Enums.EntityType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema(description = "Datos requeridos para crear o modificar una relación de seguimiento")
public class FollowRequestDTO {

   @Schema(description = "ID del seguidor (usuario o empresa)", example = "1")
   private Long followerId;

   @Schema(description = "Tipo del seguidor: DEVELOPER o COMPANY", example = "DEVELOPER")
   private EntityType followerType; //DEVELOPER - COMPANY

   @NotNull
   @Schema(description = "ID del seguido (usuario o empresa)", example = "2")
   private Long followedId;

   @NotNull
   @Schema(description = "Tipo del seguido: DEVELOPER o COMPANY", example = "COMPANY")
   private EntityType followedType; //DEVELOPER - COMPANY

   public FollowRequestDTO() {
   }

   public FollowRequestDTO(long followerId, EntityType followerType, long followedId, EntityType followedType) {
      this.followerId = followerId;
      this.followerType = followerType;
      this.followedId = followedId;
      this.followedType = followedType;
   }
}
