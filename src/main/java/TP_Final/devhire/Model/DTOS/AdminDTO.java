package TP_Final.devhire.Model.DTOS;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdminDTO {
    private Long id;
    private String name;
    private String lastName;
    private String charge;
}
