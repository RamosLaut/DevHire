package TP_Final.devhire.DTOS;

import TP_Final.devhire.Entities.Follow.DeveloperFollowsDeveloperId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FollowRequestDTO {
   @NotNull
   private Long followerId;
   @NotNull
   private String followerType; //USER - COMPANY
   @NotNull
   private Long followedId;
   @NotNull
   private String followedType; //USER - COMPANY

   public FollowRequestDTO(long followerId, String followerType, long followedId, String followedType) {
      this.followerId = followerId;
      this.followerType = followerType;
      this.followedId = followedId;
      this.followedType = followedType;
   }
}
