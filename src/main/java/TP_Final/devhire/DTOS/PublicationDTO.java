package TP_Final.devhire.DTOS;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter

public class PublicationDTO {
    Long publication_id;
    String content;
    Timestamp publicationDate;
    Long user_id;

}
