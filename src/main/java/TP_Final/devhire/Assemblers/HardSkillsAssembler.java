package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.JobController;
import TP_Final.devhire.Enums.HardSkills;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class HardSkillsAssembler implements RepresentationModelAssembler<String, EntityModel<String>> {
    @Override
    public EntityModel<String> toModel(String hardSkill) {
        return EntityModel.of(hardSkill, linkTo(methodOn(JobController.class).addHardSkill(hardSkill)));
    }

}
