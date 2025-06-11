package TP_Final.devhire.DTOS;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JobDTO {
    Long id;
    String position;
    String description;
    String location;
    String companyName;
}
