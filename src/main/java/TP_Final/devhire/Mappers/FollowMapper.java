package TP_Final.devhire.Mappers;
import TP_Final.devhire.DTOS.FollowRequestDTO;
import TP_Final.devhire.DTOS.FollowResponseDTO;
import TP_Final.devhire.Entities.Follow.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {
    @Autowired
    private ModelMapper modelMapper;
    public FollowRequestDTO convertToDTO(DeveloperFollowsDeveloper follow){
        return modelMapper.map(follow, FollowRequestDTO.class);
    }

    public Object toEntity(FollowRequestDTO dto){
        switch (dto.getFollowerType() + "_TO_" + dto.getFollowedType()) {
            case "DEVELOPER_TO_DEVELOPER" -> {
                return DeveloperFollowsDeveloper.builder().id(DeveloperFollowsDeveloperId.builder().developerFollowerId(dto.getFollowerId()).developerFollowedId(dto.getFollowedId()).build());
            }
            case "DEVELOPER_TO_COMPANY" -> {
                return DeveloperFollowsCompany.builder().id(DeveloperFollowsCompanyId.builder().developerFollowerId(dto.getFollowerId()).companyFollowedId(dto.getFollowedId()).build());
            }
            case "COMPANY_TO_COMPANY" -> {
                return CompanyFollowsCompany.builder().id(CompanyFollowsCompanyId.builder().companyFollowerId(dto.getFollowerId()).companyFollowedId(dto.getFollowedId()).build());
            }
            case "COMPANY_TO_DEVELOPER" -> {
                return CompanyFollowsDeveloper.builder().id(CompanyFollowsDeveloperId.builder().companyFollowerId(dto.getFollowerId()).developerFollowedId(dto.getFollowedId()).build());
            }
            default -> throw new IllegalArgumentException("Invalid follow-up type");
        }
    }

    public FollowResponseDTO toDTO (Object entity){
        if (entity instanceof DeveloperFollowsDeveloper){
            DeveloperFollowsDeveloper devToDev = (DeveloperFollowsDeveloper) entity;
            return  new FollowResponseDTO("DEVELOPER_TO_DEVELOPER", devToDev.getId().getDeveloperFollowerId(), devToDev.getId().getDeveloperFollowedId());
        } else if (entity instanceof DeveloperFollowsCompany) {
            DeveloperFollowsCompany devTOCom = (DeveloperFollowsCompany) entity;
            return new FollowResponseDTO("DEVELOPER_TO_COMPANY", devTOCom.getId().getDeveloperFollowerId(), devTOCom.getId().getCompanyFollowedId());
        } else if (entity instanceof CompanyFollowsCompany) {
            CompanyFollowsCompany comTOcom = (CompanyFollowsCompany) entity;
            return new FollowResponseDTO("COMPANY_TO_COMPANY", comTOcom.getId().getCompanyFollowerId(), comTOcom.getId().getCompanyFollowedId());
        } else if (entity instanceof CompanyFollowsDeveloper) {
            CompanyFollowsDeveloper comTODev = (CompanyFollowsDeveloper) entity;
            return new FollowResponseDTO("COMPANY_TO_DEVELOPER", comTODev.getId().getCompanyFollowerId(), comTODev.getId().getDeveloperFollowedId());
        }
        throw new IllegalArgumentException("Unknown entity");
    }
}
