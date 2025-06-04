package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.FollowAssembler;
import TP_Final.devhire.DTOS.FollowRequestDTO;
import TP_Final.devhire.DTOS.FollowResponseDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.DeveloperEntity;
import TP_Final.devhire.Entities.Follow.*;
import TP_Final.devhire.Mappers.FollowMapper;
import TP_Final.devhire.Repositories.*;
import TP_Final.devhire.Security.Entities.CredentialsEntity;
import TP_Final.devhire.Security.Repositories.CredentialsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {

    private final DeveloperFollowDeveloperRepository developerFollowsDeveloperRepo;
    private final DeveloperFollowCompanyRepository developerFollowsCompanyRepo;
    private final CompanyFollowDeveloperRepository companyFollowsDeveloperRepo;
    private final CompanyFollowCompanyRepository companyFollowsCompanyRepo;
    private final FollowMapper followMapper;
    private final FollowAssembler followAssembler;
    private final CompanyRepository companyRepository;
    private final DeveloperRepository developerRepository;
    private final CredentialsRepository credentialsRepository;

    @Autowired
    public FollowService(DeveloperFollowDeveloperRepository developerFollowsDeveloperRepo,
                         DeveloperFollowCompanyRepository developerFollowsCompanyRepo,
                         CompanyFollowDeveloperRepository companyFollowsDeveloperRepo,
                         CompanyFollowCompanyRepository companyFollowsCompanyRepo,
                         FollowMapper followMapper,
                         FollowAssembler followAssembler,
                         CompanyRepository companyRepository,
                         DeveloperRepository developerRepository,
                         CredentialsRepository credentialsRepository) {
        this.developerFollowsDeveloperRepo = developerFollowsDeveloperRepo;
        this.developerFollowsCompanyRepo = developerFollowsCompanyRepo;
        this.companyFollowsDeveloperRepo = companyFollowsDeveloperRepo;
        this.companyFollowsCompanyRepo = companyFollowsCompanyRepo;
        this.followMapper = followMapper;
        this.followAssembler = followAssembler;
        this.companyRepository = companyRepository;
        this.developerRepository = developerRepository;
        this.credentialsRepository = credentialsRepository;
    }

    public EntityModel<FollowResponseDTO> saveFollow(FollowRequestDTO dto) {
        validateFollowRequest(dto);

        verifyAuthenticatedUserMatchesFollower(dto);

        IFollowRelation entity = followMapper.toEntity(dto);
        IFollowRelation savedEntity;

        if (entity instanceof DeveloperFollowsDeveloper) {
            savedEntity = developerFollowsDeveloperRepo.save((DeveloperFollowsDeveloper) entity);
        } else if (entity instanceof DeveloperFollowsCompany) {
            savedEntity = developerFollowsCompanyRepo.save((DeveloperFollowsCompany) entity);
        } else if (entity instanceof CompanyFollowsDeveloper) {
            savedEntity = companyFollowsDeveloperRepo.save((CompanyFollowsDeveloper) entity);
        } else if (entity instanceof CompanyFollowsCompany) {
            savedEntity = companyFollowsCompanyRepo.save((CompanyFollowsCompany) entity);
        } else {
            throw new IllegalArgumentException("Tipo de relación de seguimiento no reconocida");
        }

        FollowResponseDTO responseDTO = followMapper.toDTO(savedEntity);
        return followAssembler.toModel(responseDTO);
    }

    public EntityModel<FollowResponseDTO> findById(String type, Long followerId, Long followedId) {
        IFollowRelation entity;
        switch (type) {
            case "DEVELOPER_TO_DEVELOPER" -> entity = developerFollowsDeveloperRepo.findById(
                            new DeveloperFollowsDeveloperId(followerId, followedId))
                    .orElseThrow(() -> new EntityNotFoundException("No follow found"));
            case "DEVELOPER_TO_COMPANY" -> entity = developerFollowsCompanyRepo.findById(
                            new DeveloperFollowsCompanyId(followerId, followedId))
                    .orElseThrow(() -> new EntityNotFoundException("No follow found"));
            case "COMPANY_TO_DEVELOPER" -> entity = companyFollowsDeveloperRepo.findById(
                            new CompanyFollowsDeveloperId(followerId, followedId))
                    .orElseThrow(() -> new EntityNotFoundException("No follow found"));
            case "COMPANY_TO_COMPANY" -> entity = companyFollowsCompanyRepo.findById(
                            new CompanyFollowsCompanyId(followerId, followedId))
                    .orElseThrow(() -> new EntityNotFoundException("No follow found"));
            default -> throw new IllegalArgumentException("Invalid follow type");
        }
        FollowResponseDTO responseDTO = followMapper.toDTO(entity);
        return followAssembler.toModel(responseDTO);
    }

    public CollectionModel<EntityModel<FollowResponseDTO>> findAll() {
        List<EntityModel<FollowResponseDTO>> allFollows = new ArrayList<>();

        developerFollowsDeveloperRepo.findAll().forEach(f -> allFollows.add(followAssembler.toModel(followMapper.toDTO(f))));
        developerFollowsCompanyRepo.findAll().forEach(f -> allFollows.add(followAssembler.toModel(followMapper.toDTO(f))));
        companyFollowsDeveloperRepo.findAll().forEach(f -> allFollows.add(followAssembler.toModel(followMapper.toDTO(f))));
        companyFollowsCompanyRepo.findAll().forEach(f -> allFollows.add(followAssembler.toModel(followMapper.toDTO(f))));

        return CollectionModel.of(allFollows);
    }

    public ResponseEntity<Void> deleteById(FollowRequestDTO dto) {
        validateFollowRequest(dto);

        verifyAuthenticatedUserMatchesFollower(dto);

        switch (dto.getFollowerType() + "_TO_" + dto.getFollowedType()) {
            case "DEVELOPER_TO_DEVELOPER" -> {
                DeveloperFollowsDeveloperId id = DeveloperFollowsDeveloperId.builder()
                        .developerFollowerId(dto.getFollowerId())
                        .developerFollowedId(dto.getFollowedId())
                        .build();
                developerFollowsDeveloperRepo.deleteById(id);
            }
            case "DEVELOPER_TO_COMPANY" -> {
                DeveloperFollowsCompanyId id = DeveloperFollowsCompanyId.builder()
                        .developerFollowerId(dto.getFollowerId())
                        .companyFollowedId(dto.getFollowedId())
                        .build();
                developerFollowsCompanyRepo.deleteById(id);
            }
            case "COMPANY_TO_DEVELOPER" -> {
                CompanyFollowsDeveloperId id = CompanyFollowsDeveloperId.builder()
                        .companyFollowerId(dto.getFollowerId())
                        .developerFollowedId(dto.getFollowedId())
                        .build();
                companyFollowsDeveloperRepo.deleteById(id);
            }
            case "COMPANY_TO_COMPANY" -> {
                CompanyFollowsCompanyId id = CompanyFollowsCompanyId.builder()
                        .companyFollowerId(dto.getFollowerId())
                        .companyFollowedId(dto.getFollowedId())
                        .build();
                companyFollowsCompanyRepo.deleteById(id);
            }
            default -> throw new IllegalArgumentException("Invalid follow type");
        }
        return ResponseEntity.noContent().build();
    }

    public EntityModel<FollowResponseDTO> deactivate(FollowRequestDTO dto) {
        IFollowRelation entity = getFollowEntity(dto);

        verifyAuthenticatedUserMatchesFollower(dto);

        setEnabled(entity, false);
        IFollowRelation saved = saveFollowEntity(dto, entity);
        return followAssembler.toModel(followMapper.toDTO(saved));
    }

    public EntityModel<FollowResponseDTO> reactivate(FollowRequestDTO dto) {
        IFollowRelation entity = getFollowEntity(dto);

        verifyAuthenticatedUserMatchesFollower(dto);

        setEnabled(entity, true);
        IFollowRelation saved = saveFollowEntity(dto, entity);
        return followAssembler.toModel(followMapper.toDTO(saved));
    }

    public CollectionModel<EntityModel<FollowResponseDTO>> getFollowers(String type, Long id) {
        List<EntityModel<FollowResponseDTO>> followers = new ArrayList<>();

        switch (type) {
            case "DEVELOPER" -> {
                developerFollowsDeveloperRepo.findByIdDeveloperFollowedIdAndEnabledTrue(id)
                        .forEach(f -> followers.add(followAssembler.toModel(followMapper.toDTO(f))));
                companyFollowsDeveloperRepo.findByIdDeveloperFollowedIdAndEnabledTrue(id)
                        .forEach(f -> followers.add(followAssembler.toModel(followMapper.toDTO(f))));
            }
            case "COMPANY" -> {
                developerFollowsCompanyRepo.findByIdCompanyFollowedIdAndEnabledTrue(id)
                        .forEach(f -> followers.add(followAssembler.toModel(followMapper.toDTO(f))));
                companyFollowsCompanyRepo.findByIdCompanyFollowedIdAndEnabledTrue(id)
                        .forEach(f -> followers.add(followAssembler.toModel(followMapper.toDTO(f))));
            }
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        }

        return CollectionModel.of(followers);
    }

    public CollectionModel<EntityModel<FollowResponseDTO>> getFollowings(String type, Long id) {
        List<EntityModel<FollowResponseDTO>> followings = new ArrayList<>();

        switch (type) {
            case "DEVELOPER" -> {
                developerFollowsDeveloperRepo.findByIdDeveloperFollowerIdAndEnabledTrue(id)
                        .forEach(f -> followings.add(followAssembler.toModel(followMapper.toDTO(f))));
                developerFollowsCompanyRepo.findByIdDeveloperFollowerIdAndEnabledTrue(id)
                        .forEach(f -> followings.add(followAssembler.toModel(followMapper.toDTO(f))));
            }
            case "COMPANY" -> {
                companyFollowsDeveloperRepo.findByIdCompanyFollowerIdAndEnabledTrue(id)
                        .forEach(f -> followings.add(followAssembler.toModel(followMapper.toDTO(f))));
                companyFollowsCompanyRepo.findByIdCompanyFollowerIdAndEnabledTrue(id)
                        .forEach(f -> followings.add(followAssembler.toModel(followMapper.toDTO(f))));
            }
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        }

        return CollectionModel.of(followings);
    }

    // Métodos auxiliares:

    private void validateFollowRequest(FollowRequestDTO dto) {
        if (dto.getFollowerId() == null || dto.getFollowedId() == null) {
            throw new IllegalArgumentException("IDs cannot be null");
        }

        if (dto.getFollowerType() == null || dto.getFollowedType() == null) {
            throw new IllegalArgumentException("Types cannot be null");
        }

        if (dto.getFollowerId().equals(dto.getFollowedId()) &&
                dto.getFollowerType().equals(dto.getFollowedType())) {
            throw new IllegalArgumentException("A developer or company cannot follow itself");
        }

        Set<String> validTypes = Set.of("DEVELOPER", "COMPANY");
        if (!validTypes.contains(dto.getFollowerType()) || !validTypes.contains(dto.getFollowedType())) {
            throw new IllegalArgumentException("Invalid types");
        }
    }

    private IFollowRelation getFollowEntity(FollowRequestDTO dto) {
        return switch (dto.getFollowerType() + "_TO_" + dto.getFollowedType()) {
            case "DEVELOPER_TO_DEVELOPER" -> developerFollowsDeveloperRepo.findById(
                            new DeveloperFollowsDeveloperId(dto.getFollowerId(), dto.getFollowedId()))
                    .orElseThrow(() -> new EntityNotFoundException("Follow not found"));
            case "DEVELOPER_TO_COMPANY" -> developerFollowsCompanyRepo.findById(
                            new DeveloperFollowsCompanyId(dto.getFollowerId(), dto.getFollowedId()))
                    .orElseThrow(() -> new EntityNotFoundException("Follow not found"));
            case "COMPANY_TO_DEVELOPER" -> companyFollowsDeveloperRepo.findById(
                            new CompanyFollowsDeveloperId(dto.getFollowerId(), dto.getFollowedId()))
                    .orElseThrow(() -> new EntityNotFoundException("Follow not found"));
            case "COMPANY_TO_COMPANY" -> companyFollowsCompanyRepo.findById(
                            new CompanyFollowsCompanyId(dto.getFollowerId(), dto.getFollowedId()))
                    .orElseThrow(() -> new EntityNotFoundException("Follow not found"));
            default -> throw new IllegalArgumentException("Invalid follow type");
        };
    }

    private void setEnabled(Object entity, boolean value) {
        if (entity instanceof DeveloperFollowsDeveloper dfd) {
            dfd.setEnabled(value);
        } else if (entity instanceof DeveloperFollowsCompany dfc) {
            dfc.setEnabled(value);
        } else if (entity instanceof CompanyFollowsDeveloper cfd) {
            cfd.setEnabled(value);
        } else if (entity instanceof CompanyFollowsCompany cfc) {
            cfc.setEnabled(value);
        } else {
            throw new IllegalArgumentException("Unknown entity type");
        }
    }

    private IFollowRelation saveFollowEntity(FollowRequestDTO dto, IFollowRelation entity) {
        return switch (dto.getFollowerType() + "_TO_" + dto.getFollowedType()) {
            case "DEVELOPER_TO_DEVELOPER" -> developerFollowsDeveloperRepo.save((DeveloperFollowsDeveloper) entity);
            case "DEVELOPER_TO_COMPANY" -> developerFollowsCompanyRepo.save((DeveloperFollowsCompany) entity);
            case "COMPANY_TO_DEVELOPER" -> companyFollowsDeveloperRepo.save((CompanyFollowsDeveloper) entity);
            case "COMPANY_TO_COMPANY" -> companyFollowsCompanyRepo.save((CompanyFollowsCompany) entity);
            default -> throw new IllegalArgumentException("Invalid follow type");
        };
    }

    private void verifyAuthenticatedUserMatchesFollower(FollowRequestDTO dto) {
        String loggedUserEmail = getLoggedUserEmail();

        CredentialsEntity credentials = credentialsRepository.findByEmail(loggedUserEmail)
                .orElseThrow(() -> new AccessDeniedException("User not authorized"));

        if (dto.getFollowerType().equals("DEVELOPER")) {
            DeveloperEntity developer = credentials.getDeveloper();
            if (developer == null || !developer.getId().equals(dto.getFollowerId())) {
                throw new AccessDeniedException("Cannot create or delete follow in another developer's name");
            }
        } else if (dto.getFollowerType().equals("COMPANY")) {
            CompanyEntity company = credentials.getCompany();
            if (company == null || !company.getId().equals(dto.getFollowerId())) {
                throw new AccessDeniedException("Cannot create or delete follow in another company's name");
            }
        } else {
            throw new AccessDeniedException("Invalid follower type");
        }
    }

    private String getLoggedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User is not authenticated");
        }
        return authentication.getName();
    }
}

