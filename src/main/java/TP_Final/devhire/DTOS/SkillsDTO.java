package TP_Final.devhire.DTOS;

import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.SoftSkills;
import lombok.Data;

import java.util.List;
@Data
public class SkillsDTO {
    private List<HardSkills> hardSkills;
    private List<SoftSkills> softSkills;
}
