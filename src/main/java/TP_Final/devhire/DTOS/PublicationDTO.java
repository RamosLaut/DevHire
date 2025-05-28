package TP_Final.devhire.DTOS;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter

public class PublicationDTO {
    Long id;
    String content;
    Timestamp publicationDate;
    Long user_id;
    Long company_id;
}
