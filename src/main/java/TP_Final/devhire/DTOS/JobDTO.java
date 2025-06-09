package TP_Final.devhire.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobDTO {
    Long id;
    String position;
    String description;
    String location;
    String companyName;
}
