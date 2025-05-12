package TP_Final.devhire.Entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Embeddable
public class JobExperience {
    private Long Experience_id;
    private String company;
    private String position;
    private Integer years;

}
