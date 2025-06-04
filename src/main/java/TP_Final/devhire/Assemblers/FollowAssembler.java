package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.CompanyController;
import TP_Final.devhire.Controllers.DeveloperController;
import TP_Final.devhire.Controllers.FollowController;
import TP_Final.devhire.DTOS.FollowResponseDTO;
import TP_Final.devhire.Mappers.FollowMapper;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FollowAssembler implements RepresentationModelAssembler<FollowResponseDTO, EntityModel<FollowResponseDTO>> {

    private final FollowMapper followMapper;

    public FollowAssembler(FollowMapper followMapper) {
        this.followMapper = followMapper;
    }

    @Override
    public @NonNull EntityModel<FollowResponseDTO> toModel(FollowResponseDTO dto) {
        return switch (dto.getType()) {
            case "DEVELOPER_TO_DEVELOPER" -> EntityModel.of(dto,
                    linkTo(methodOn(FollowController.class).findById(dto.getType(), dto.getFollowerId(), dto.getFollowedId())).withSelfRel(),
                    linkTo(methodOn(DeveloperController.class).getDevById(dto.getFollowerId())).withRel("followerDeveloper"),
                    linkTo(methodOn(DeveloperController.class).getDevById(dto.getFollowedId())).withRel("followedDeveloper"));
            case "COMPANY_TO_COMPANY" -> EntityModel.of(dto,
                    linkTo(methodOn(FollowController.class).findById(dto.getType(), dto.getFollowerId(), dto.getFollowedId())).withSelfRel(),
                    linkTo(methodOn(CompanyController.class).findById(dto.getFollowerId())).withRel("followerCompany"),
                    linkTo(methodOn(CompanyController.class).findById(dto.getFollowedId())).withRel("followedCompany"));
            case "COMPANY_TO_DEVELOPER" -> EntityModel.of(dto,
                    linkTo(methodOn(FollowController.class).findById(dto.getType(), dto.getFollowerId(), dto.getFollowedId())).withSelfRel(),
                    linkTo(methodOn(CompanyController.class).findById(dto.getFollowerId())).withRel("followerCompany"),
                    linkTo(methodOn(DeveloperController.class).getDevById(dto.getFollowedId())).withRel("followedDeveloper"));
            case "DEVELOPER_TO_COMPANY" -> EntityModel.of(dto,
                    linkTo(methodOn(FollowController.class).findById(dto.getType(), dto.getFollowerId(), dto.getFollowedId())).withSelfRel(),
                    linkTo(methodOn(DeveloperController.class).getDevById(dto.getFollowerId())).withRel("followerDeveloper"),
                    linkTo(methodOn(CompanyController.class).findById(dto.getFollowedId())).withRel("followedCompany"));
            default -> throw new IllegalArgumentException("Invalid follow-up type");
        };
    }
}
