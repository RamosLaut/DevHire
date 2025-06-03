package TP_Final.devhire.DTOS;

import TP_Final.devhire.Entities.Follow.DeveloperFollowsDeveloperId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FollowRequestDTO {
   private long followerId;
   private String followerType; //USER - COMPANY
   private long followedId;
   private String followedType; //USER - COMPANY

}
