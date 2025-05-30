package TP_Final.devhire.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter

public class UserPublicationDTO {
    Long id;
    String content;
    Timestamp publicationDate;
    String userName;
}
