package TP_Final.devhire.Model.DTOS;

import TP_Final.devhire.Model.Enums.HardSkills;
import TP_Final.devhire.Model.Enums.SoftSkills;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class SkillsDTO {
    private List<HardSkills> hardSkills;
    private List<SoftSkills> softSkills;
}
