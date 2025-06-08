package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.LikeController;
import TP_Final.devhire.DTOS.CompanyLikeDTO;
import TP_Final.devhire.DTOS.DeveloperLikeDTO;
import TP_Final.devhire.Entities.LikeEntity;
import TP_Final.devhire.Mappers.LikeMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class LikeAssembler implements RepresentationModelAssembler<LikeEntity, EntityModel<Object>> {
    @Autowired
    LikeMapper likeMapper;
    @Override
    public @NonNull EntityModel<Object> toModel(LikeEntity like) {
        if(like.getCompany()!=null){
            CompanyLikeDTO companyLikeDTO = likeMapper.convertToCompanyLikeDTO(like);
            return EntityModel.of(companyLikeDTO, linkTo(methodOn(LikeController.class).findById(like.getId())).withSelfRel(),
                    linkTo(methodOn(LikeController.class).deleteById(like.getId())).withRel("Delete like"));
        }else{
            DeveloperLikeDTO developerLikeDTO = likeMapper.convertToDeveloperLikeDTO(like);
            return EntityModel.of(developerLikeDTO, linkTo(methodOn(LikeController.class).findById(like.getId())).withSelfRel(),
                    linkTo(methodOn(LikeController.class).deleteById(like.getId())).withRel("Delete"));
        }
    }
}
