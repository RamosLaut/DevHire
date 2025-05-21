package TP_Final.devhire.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class LikeDTO {
    private long like_id;
    private long user_id;
    private long publication_id;
    private Timestamp likeDate;
}
