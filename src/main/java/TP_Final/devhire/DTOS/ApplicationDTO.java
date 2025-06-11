package TP_Final.devhire.DTOS;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ApplicationDTO {
    private Long id;
    private Long applicationDevId;
    private LocalDateTime postulationDate;
    private String jobPosition;
    private String jobCompanyName;
}
