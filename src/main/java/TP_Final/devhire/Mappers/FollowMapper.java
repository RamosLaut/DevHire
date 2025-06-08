package TP_Final.devhire.Mappers;
import TP_Final.devhire.DTOS.FollowRequestDTO;
import TP_Final.devhire.DTOS.FollowResponseDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.DeveloperEntity;
import TP_Final.devhire.Entities.Follow.*;
import TP_Final.devhire.Entities.Follow.IFollowRelation;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.DeveloperRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {
    private final ModelMapper modelMapper;
    private final DeveloperRepository developerRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public FollowMapper(ModelMapper modelMapper, DeveloperRepository developerRepository, CompanyRepository companyRepository) {
        this.modelMapper = modelMapper;
        this.developerRepository = developerRepository;
        this.companyRepository = companyRepository;
    }

    public FollowRequestDTO convertToDTO(DeveloperFollowsDeveloper follow){
        return modelMapper.map(follow, FollowRequestDTO.class);
    }

    public IFollowRelation toEntity(FollowRequestDTO dto) {
        Long followerId = dto.getFollowerId();
        Long followedId = dto.getFollowedId();

        return switch (dto.getFollowerType() + "_TO_" + dto.getFollowedType()) {
            case "DEVELOPER_TO_DEVELOPER" -> {
                DeveloperEntity follower = developerRepository.findById(followerId).orElseThrow();
                DeveloperEntity followed = developerRepository.findById(followedId).orElseThrow();
                yield DeveloperFollowsDeveloper.builder()
                        .id(DeveloperFollowsDeveloperId.builder()
                                .developerFollowerId(followerId)
                                .developerFollowedId(followedId)
                                .build())
                        .follower(follower)
                        .followed(followed)
                        .enabled(true)
                        .build();
            }
            case "DEVELOPER_TO_COMPANY" -> {
                DeveloperEntity follower = developerRepository.findById(followerId).orElseThrow();
                CompanyEntity followed = companyRepository.findById(followedId).orElseThrow();
                yield DeveloperFollowsCompany.builder()
                        .id(DeveloperFollowsCompanyId.builder()
                                .developerFollowerId(followerId)
                                .companyFollowedId(followedId)
                                .build())
                        .follower(follower)
                        .followed(followed)
                        .enabled(true)
                        .build();
            }
            case "COMPANY_TO_COMPANY" -> {
                CompanyEntity follower = companyRepository.findById(followerId).orElseThrow();
                CompanyEntity followed = companyRepository.findById(followedId).orElseThrow();
                yield CompanyFollowsCompany.builder()
                        .id(CompanyFollowsCompanyId.builder()
                                .companyFollowerId(followerId)
                                .companyFollowedId(followedId)
                                .build())
                        .follower(follower)
                        .followed(followed)
                        .enabled(true)
                        .build();
            }
            case "COMPANY_TO_DEVELOPER" -> {
                CompanyEntity follower = companyRepository.findById(followerId).orElseThrow();
                DeveloperEntity followed = developerRepository.findById(followedId).orElseThrow();
                yield CompanyFollowsDeveloper.builder()
                        .id(CompanyFollowsDeveloperId.builder()
                                .companyFollowerId(followerId)
                                .developerFollowedId(followedId)
                                .build())
                        .follower(follower)
                        .followed(followed)
                        .enabled(true)
                        .build();
            }
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
