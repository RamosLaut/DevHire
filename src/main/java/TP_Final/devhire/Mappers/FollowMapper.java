package TP_Final.devhire.Mappers;
import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Entities.FollowEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {
    @Autowired
    private ModelMapper modelMapper;
    public DevFollowDevDTO convertToDevFollowDevDTO(FollowEntity follow){
        return modelMapper.map(follow, DevFollowDevDTO.class);
    }
    public FollowEntity convertToEntityFromDevFollowDevDTO(DevFollowDevDTO devFollowDevDTO){
        return modelMapper.map(devFollowDevDTO,FollowEntity.class);
    }
    public DevFollowCompanyDTO convertToDevFollowCompanyDTO(FollowEntity follow){
        return modelMapper.map(follow, DevFollowCompanyDTO.class);
    }
    public FollowEntity convertToEntityFromDevFollowCompanyDTO(DevFollowCompanyDTO devFollowCompanyDTO){
        return modelMapper.map(devFollowCompanyDTO,FollowEntity.class);
    }
    public CompanyFollowDeveloperDTO convertToCompanyFollowDevDTO(FollowEntity follow){
        return modelMapper.map(follow, CompanyFollowDeveloperDTO.class);
    }
    public FollowEntity convertToEntityFromCompanyFollowDevDTO(CompanyFollowDeveloperDTO companyFollowDevDTO){
        return modelMapper.map(companyFollowDevDTO,FollowEntity.class);
    }
    public CompanyFollowCompanyDTO convertToCompanyFollowCompanyDTO(FollowEntity follow){
        return modelMapper.map(follow, CompanyFollowCompanyDTO.class);
    }
    public FollowEntity convertToEntityFromCompanyFollowCompanyDTO(CompanyFollowCompanyDTO companyFollowCompanyDTO){
        return modelMapper.map(companyFollowCompanyDTO,FollowEntity.class);
    }
}
