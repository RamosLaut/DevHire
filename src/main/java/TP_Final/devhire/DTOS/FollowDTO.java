package TP_Final.devhire.DTOS;

import TP_Final.devhire.Entities.FollowId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FollowDTO {
   FollowId id;
   LocalDateTime followUpDate;
   Boolean state;
}
