package TP_Final.devhire.Mappers;
import TP_Final.devhire.DTOS.FollowRequestDTO;
import TP_Final.devhire.DTOS.FollowResponseDTO;
import TP_Final.devhire.Entities.Follow.*;
import TP_Final.devhire.Entities.Follow.IFollowRelation;
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

    public IFollowRelation toEntity(FollowRequestDTO dto) {
        return switch (dto.getFollowerType() + "_TO_" + dto.getFollowedType()) {
            case "DEVELOPER_TO_DEVELOPER" -> DeveloperFollowsDeveloper.builder()
                    .id(DeveloperFollowsDeveloperId.builder()
                            .developerFollowerId(dto.getFollowerId())
                            .developerFollowedId(dto.getFollowedId())
                            .build())
                    .build();
            case "DEVELOPER_TO_COMPANY" -> DeveloperFollowsCompany.builder()
                    .id(DeveloperFollowsCompanyId.builder()
                            .developerFollowerId(dto.getFollowerId())
                            .companyFollowedId(dto.getFollowedId())
                            .build())
                    .build();
            case "COMPANY_TO_COMPANY" -> CompanyFollowsCompany.builder()
                    .id(CompanyFollowsCompanyId.builder()
                            .companyFollowerId(dto.getFollowerId())
                            .companyFollowedId(dto.getFollowedId())
                            .build())
                    .build();
            case "COMPANY_TO_DEVELOPER" -> CompanyFollowsDeveloper.builder()
                    .id(CompanyFollowsDeveloperId.builder()
                            .companyFollowerId(dto.getFollowerId())
                            .developerFollowedId(dto.getFollowedId())
                            .build())
                    .build();
            default -> throw new IllegalArgumentException("Invalid follow-up type");
        };
    }

    public FollowResponseDTO toDTO(IFollowRelation relation) {
        if (relation instanceof DeveloperFollowsDeveloper rel) {
            return new FollowResponseDTO("DEVELOPER_TO_DEVELOPER", rel.getId().getDeveloperFollowerId(), rel.getId().getDeveloperFollowedId());
        } else if (relation instanceof DeveloperFollowsCompany rel) {
            return new FollowResponseDTO("DEVELOPER_TO_COMPANY", rel.getId().getDeveloperFollowerId(), rel.getId().getCompanyFollowedId());
        } else if (relation instanceof CompanyFollowsCompany rel) {
            return new FollowResponseDTO("COMPANY_TO_COMPANY", rel.getId().getCompanyFollowerId(), rel.getId().getCompanyFollowedId());
        } else if (relation instanceof CompanyFollowsDeveloper rel) {
            return new FollowResponseDTO("COMPANY_TO_DEVELOPER", rel.getId().getCompanyFollowerId(), rel.getId().getDeveloperFollowedId());
        }
        throw new IllegalArgumentException("Unknown relation type");
    }
}
