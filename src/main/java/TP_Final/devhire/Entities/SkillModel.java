package TP_Final.devhire.Entities;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
public class SkillModel extends RepresentationModel<SkillModel> {
    private String nombre;

    public SkillModel(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
