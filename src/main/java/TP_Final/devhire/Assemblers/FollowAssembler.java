package TP_Final.devhire.Assemblers;

import TP_Final.devhire.DTOS.CompanyFollowCompanyDTO;
import TP_Final.devhire.DTOS.CompanyFollowDeveloperDTO;
import TP_Final.devhire.DTOS.DevFollowCompanyDTO;
import TP_Final.devhire.DTOS.DevFollowDevDTO;
import TP_Final.devhire.Entities.FollowEntity;
import TP_Final.devhire.Mappers.FollowMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class FollowAssembler implements RepresentationModelAssembler<FollowEntity, EntityModel<Object>> {
    private final FollowMapper mapper;
    @Autowired
    public FollowAssembler(FollowMapper mapper) {
        this.mapper = mapper;
    }
    @Override
    public @NonNull EntityModel<Object> toModel(FollowEntity follow) {
        if(follow.getDevFollower()!=null && follow.getDevFollowed()!=null){
            DevFollowDevDTO devFollowDev = mapper.convertToDevFollowDevDTO(follow);
            return EntityModel.of(devFollowDev);
        } else if (follow.getDevFollower()!=null && follow.getCompanyFollowed()!=null) {
            DevFollowCompanyDTO devFollowCompany = mapper.convertToDevFollowCompanyDTO(follow);
            return EntityModel.of(devFollowCompany);
        } else if (follow.getCompanyFollower()!=null && follow.getCompanyFollowed()!=null) {
            CompanyFollowCompanyDTO companyFollowCompany = mapper.convertToCompanyFollowCompanyDTO(follow);
            return EntityModel.of(companyFollowCompany);
        } else {
            CompanyFollowDeveloperDTO companyFollowDeveloper = mapper.convertToCompanyFollowDevDTO(follow);
            return EntityModel.of(companyFollowDeveloper);
        }
    }

}
