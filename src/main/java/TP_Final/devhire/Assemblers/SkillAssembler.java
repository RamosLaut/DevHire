package TP_Final.devhire.Assemblers;
import TP_Final.devhire.Controllers.JobController;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.SkillModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SkillAssembler {
    public TP_Final.devhire.Model.Mappers.Mappers.Entities.SkillModel toModel(String skill, Long idEmpleo) {
        TP_Final.devhire.Model.Mappers.Mappers.Entities.SkillModel model = new TP_Final.devhire.Model.Mappers.Mappers.Entities.SkillModel(skill);

        Link link = linkTo(methodOn(JobController.class)
                .addRequirement(idEmpleo, skill))
                .withRel("Add requirement");
        model.add(link);
        return model;
    }

    public CollectionModel<TP_Final.devhire.Model.Mappers.Mappers.Entities.SkillModel> toCollection(List<String> skills, Long idEmpleo) {
        List<TP_Final.devhire.Model.Mappers.Mappers.Entities.SkillModel> modelos = skills.stream()
                .map(skill -> toModel(skill, idEmpleo))
                .toList();

        return CollectionModel.of(modelos);
    }
}
