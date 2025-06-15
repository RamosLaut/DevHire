package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.FollowAssembler;
import TP_Final.devhire.Model.DTOS.FollowRequestDTO;
import TP_Final.devhire.Model.DTOS.FollowResponseDTO;
import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Entities.DeveloperEntity;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.*;
import TP_Final.devhire.Model.Enums.EntityType;
import TP_Final.devhire.Model.Enums.FollowType;
import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Model.Mappers.FollowMapper;
import TP_Final.devhire.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static TP_Final.devhire.Model.Enums.EntityType.COMPANY;
import static TP_Final.devhire.Model.Enums.EntityType.DEVELOPER;

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

    @Autowired
    public FollowService(DeveloperFollowDeveloperRepository developerFollowsDeveloperRepo,
                         DeveloperFollowCompanyRepository developerFollowsCompanyRepo,
                         CompanyFollowDeveloperRepository companyFollowsDeveloperRepo,
                         CompanyFollowCompanyRepository companyFollowsCompanyRepo,
                         FollowMapper followMapper,
                         FollowAssembler followAssembler,
                         CompanyRepository companyRepository,
                         DeveloperRepository developerRepository) {
        this.developerFollowsDeveloperRepo = developerFollowsDeveloperRepo;
        this.developerFollowsCompanyRepo = developerFollowsCompanyRepo;
        this.companyFollowsDeveloperRepo = companyFollowsDeveloperRepo;
        this.companyFollowsCompanyRepo = companyFollowsCompanyRepo;
        this.followMapper = followMapper;
        this.followAssembler = followAssembler;
        this.companyRepository = companyRepository;
        this.developerRepository = developerRepository;
    }

    public EntityModel<FollowResponseDTO> saveFollow(FollowRequestDTO dto) {
        completeFollowerFromCredentials(dto);
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
            throw new IllegalArgumentException("Invalid follow type");
        }

        FollowResponseDTO responseDTO = followMapper.toDTO(savedEntity);
        return followAssembler.toModel(responseDTO);
    }

    public EntityModel<FollowResponseDTO> findById(FollowType type, Long followerId, Long followedId) {
        IFollowRelation entity;
        switch (type) {
            case DEVELOPER_TO_DEVELOPER -> {
                DeveloperFollowsDeveloperId id = new DeveloperFollowsDeveloperId(followerId, followedId);
                entity = developerFollowsDeveloperRepo.findById(id)
                        .orElseThrow(() -> new NotFoundException("No follow found for DEVELOPER_TO_DEVELOPER with IDs " + followerId + " -> " + followedId));
            }
            case DEVELOPER_TO_COMPANY -> {
                DeveloperFollowsCompanyId id = new DeveloperFollowsCompanyId(followerId, followedId);
                entity = developerFollowsCompanyRepo.findById(id)
                        .orElseThrow(() -> new NotFoundException("No follow found for DEVELOPER_TO_COMPANY with IDs " + followerId + " -> " + followedId));
            }
            case COMPANY_TO_DEVELOPER -> {
                CompanyFollowsDeveloperId id = new CompanyFollowsDeveloperId(followerId, followedId);
                entity = companyFollowsDeveloperRepo.findById(id)
                        .orElseThrow(() -> new NotFoundException("No follow found for COMPANY_TO_DEVELOPER with IDs " + followerId + " -> " + followedId));
            }
            case COMPANY_TO_COMPANY -> {
                CompanyFollowsCompanyId id = new CompanyFollowsCompanyId(followerId, followedId);
                entity = companyFollowsCompanyRepo.findById(id)
                        .orElseThrow(() -> new NotFoundException("No follow found for COMPANY_TO_COMPANY with IDs " + followerId + " -> " + followedId));
            }
            default -> throw new IllegalArgumentException("Invalid follow type: " + type);
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

        FollowType type = FollowType.valueOf(dto.getFollowerType() + "_TO_" + dto.getFollowedType());

        switch (type) {
            case DEVELOPER_TO_DEVELOPER -> {
                DeveloperFollowsDeveloperId id = DeveloperFollowsDeveloperId.builder()
                        .developerFollowerId(dto.getFollowerId())
                        .developerFollowedId(dto.getFollowedId())
                        .build();
                if (!developerFollowsDeveloperRepo.existsById(id)) {
                    throw new NotFoundException("No follow found for DEVELOPER_TO_DEVELOPER with IDs " + dto.getFollowerId() + " -> " + dto.getFollowedId());
                }
                developerFollowsDeveloperRepo.deleteById(id);
            }
            case DEVELOPER_TO_COMPANY -> {
                DeveloperFollowsCompanyId id = DeveloperFollowsCompanyId.builder()
                        .developerFollowerId(dto.getFollowerId())
                        .companyFollowedId(dto.getFollowedId())
                        .build();
                if (!developerFollowsCompanyRepo.existsById(id)) {
                    throw new NotFoundException("No follow found for DEVELOPER_TO_COMPANY with IDs " + dto.getFollowerId() + " -> " + dto.getFollowedId());
                }
                developerFollowsCompanyRepo.deleteById(id);
            }
            case COMPANY_TO_DEVELOPER -> {
                CompanyFollowsDeveloperId id = CompanyFollowsDeveloperId.builder()
                        .companyFollowerId(dto.getFollowerId())
                        .developerFollowedId(dto.getFollowedId())
                        .build();
                if (!companyFollowsDeveloperRepo.existsById(id)) {
                    throw new NotFoundException("No follow found for COMPANY_TO_DEVELOPER with IDs " + dto.getFollowerId() + " -> " + dto.getFollowedId());
                }
                companyFollowsDeveloperRepo.deleteById(id);
            }
            case COMPANY_TO_COMPANY -> {
                CompanyFollowsCompanyId id = CompanyFollowsCompanyId.builder()
                        .companyFollowerId(dto.getFollowerId())
                        .companyFollowedId(dto.getFollowedId())
                        .build();
                if (!companyFollowsCompanyRepo.existsById(id)) {
                    throw new NotFoundException("No follow found for COMPANY_TO_COMPANY with IDs " + dto.getFollowerId() + " -> " + dto.getFollowedId());
                }
                companyFollowsCompanyRepo.deleteById(id);
            }
            default -> throw new IllegalArgumentException("Invalid follow type: " + type);
        }
        return ResponseEntity.noContent().build();
    }

    public EntityModel<FollowResponseDTO> deactivate(FollowRequestDTO dto) {
        completeFollowerFromCredentials(dto);
        validateFollowRequest(dto);
        verifyAuthenticatedUserMatchesFollower(dto);

        IFollowRelation entity = getFollowEntity(dto);
        setEnabled(entity, false);
        IFollowRelation saved = saveFollowEntity(dto, entity);
        return followAssembler.toModel(followMapper.toDTO(saved));
    }

    public EntityModel<FollowResponseDTO> reactivate(FollowRequestDTO dto) {
        completeFollowerFromCredentials(dto);
        validateFollowRequest(dto);
        verifyAuthenticatedUserMatchesFollower(dto);

        IFollowRelation entity = getFollowEntity(dto);
        setEnabled(entity, true);
        IFollowRelation saved = saveFollowEntity(dto, entity);
        return followAssembler.toModel(followMapper.toDTO(saved));
    }

    public CollectionModel<EntityModel<FollowResponseDTO>> getFollowers(EntityType type, Long id) {
        List<EntityModel<FollowResponseDTO>> followers = new ArrayList<>();

        switch (type) {
            case DEVELOPER -> {
                developerFollowsDeveloperRepo.findByIdDeveloperFollowedIdAndEnabledTrue(id)
                        .forEach(f -> followers.add(followAssembler.toModel(followMapper.toDTO(f))));
                companyFollowsDeveloperRepo.findByIdDeveloperFollowedIdAndEnabledTrue(id)
                        .forEach(f -> followers.add(followAssembler.toModel(followMapper.toDTO(f))));
            }
            case COMPANY -> {
                developerFollowsCompanyRepo.findByIdCompanyFollowedIdAndEnabledTrue(id)
                        .forEach(f -> followers.add(followAssembler.toModel(followMapper.toDTO(f))));
                companyFollowsCompanyRepo.findByIdCompanyFollowedIdAndEnabledTrue(id)
                        .forEach(f -> followers.add(followAssembler.toModel(followMapper.toDTO(f))));
            }
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        }

        return CollectionModel.of(followers);
    }

    public CollectionModel<EntityModel<FollowResponseDTO>> getFollowings(EntityType type, Long id) {
        List<EntityModel<FollowResponseDTO>> followings = new ArrayList<>();

        switch (type) {
            case DEVELOPER -> {
                developerFollowsDeveloperRepo.findByIdDeveloperFollowerIdAndEnabledTrue(id)
                        .forEach(f -> followings.add(followAssembler.toModel(followMapper.toDTO(f))));
                developerFollowsCompanyRepo.findByIdDeveloperFollowerIdAndEnabledTrue(id)
                        .forEach(f -> followings.add(followAssembler.toModel(followMapper.toDTO(f))));
            }
            case COMPANY -> {
                companyFollowsDeveloperRepo.findByIdCompanyFollowerIdAndEnabledTrue(id)
                        .forEach(f -> followings.add(followAssembler.toModel(followMapper.toDTO(f))));
                companyFollowsCompanyRepo.findByIdCompanyFollowerIdAndEnabledTrue(id)
                        .forEach(f -> followings.add(followAssembler.toModel(followMapper.toDTO(f))));
            }
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        }

        return CollectionModel.of(followings);
    }
    public CollectionModel<EntityModel<FollowResponseDTO>> getOwnFollowers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<DeveloperEntity> optionalDev = developerRepository.findByCredentials_Email(email);
        Optional<CompanyEntity> optionalCompany = companyRepository.findByCredentials_Email(email);

        if (optionalDev.isPresent()) {
            Long id = optionalDev.get().getId();
            return getFollowers(DEVELOPER, id);
        } else if (optionalCompany.isPresent()) {
            Long id = optionalCompany.get().getId();
            return getFollowers(COMPANY, id);
        } else {
            throw new NotFoundException("Authenticated user not found in developer or company tables.");
        }
    }

    public CollectionModel<EntityModel<FollowResponseDTO>> getOwnFollowings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<DeveloperEntity> optionalDev = developerRepository.findByCredentials_Email(email);
        Optional<CompanyEntity> optionalCompany = companyRepository.findByCredentials_Email(email);

        if (optionalDev.isPresent()) {
            Long id = optionalDev.get().getId();
            return getFollowings(DEVELOPER, id);
        } else if (optionalCompany.isPresent()) {
            Long id = optionalCompany.get().getId();
            return getFollowings(COMPANY, id);
        } else {
            throw new NotFoundException("Authenticated user not found in developer or company tables.");
        }
    }


    // Métodos auxiliares:

    private void validateFollowRequest(FollowRequestDTO dto) {
        System.out.println("Follower type: '" + dto.getFollowerType() + "'");
        System.out.println("Followed type: '" + dto.getFollowedType() + "'");

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

        Set<EntityType> validTypes = Set.of(EntityType.DEVELOPER, EntityType.COMPANY);
        if (!validTypes.contains(dto.getFollowerType()) || !validTypes.contains(dto.getFollowedType())) {
            throw new IllegalArgumentException("Invalid types");
        }
    }

    private IFollowRelation getFollowEntity(FollowRequestDTO dto) {
        FollowType type = FollowType.from(
                dto.getFollowerType(),
                dto.getFollowedType()
        );
        return switch (type) {
            case DEVELOPER_TO_DEVELOPER -> developerFollowsDeveloperRepo.findById(
                            new DeveloperFollowsDeveloperId(dto.getFollowerId(), dto.getFollowedId()))
                    .orElseThrow(() -> new NotFoundException("Follow not found"));
            case DEVELOPER_TO_COMPANY -> developerFollowsCompanyRepo.findById(
                            new DeveloperFollowsCompanyId(dto.getFollowerId(), dto.getFollowedId()))
                    .orElseThrow(() -> new NotFoundException("Follow not found"));
            case COMPANY_TO_DEVELOPER -> companyFollowsDeveloperRepo.findById(
                            new CompanyFollowsDeveloperId(dto.getFollowerId(), dto.getFollowedId()))
                    .orElseThrow(() -> new NotFoundException("Follow not found"));
            case COMPANY_TO_COMPANY -> companyFollowsCompanyRepo.findById(
                            new CompanyFollowsCompanyId(dto.getFollowerId(), dto.getFollowedId()))
                    .orElseThrow(() -> new NotFoundException("Follow not found"));
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
        FollowType type = FollowType.from(
                dto.getFollowerType(),
                dto.getFollowedType()
        );
        return switch (type) {
            case DEVELOPER_TO_DEVELOPER -> developerFollowsDeveloperRepo.save((DeveloperFollowsDeveloper) entity);
            case DEVELOPER_TO_COMPANY -> developerFollowsCompanyRepo.save((DeveloperFollowsCompany) entity);
            case COMPANY_TO_DEVELOPER -> companyFollowsDeveloperRepo.save((CompanyFollowsDeveloper) entity);
            case COMPANY_TO_COMPANY -> companyFollowsCompanyRepo.save((CompanyFollowsCompany) entity);
        };
    }

    private void verifyAuthenticatedUserMatchesFollower(FollowRequestDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<DeveloperEntity> devOpt = developerRepository.findByCredentials_Email(email);
        Optional<CompanyEntity> companyOpt = companyRepository.findByCredentials_Email(email);

        if (devOpt.isPresent()) {
            DeveloperEntity developer = devOpt.get();

            if (dto.getFollowerId() == null) dto.setFollowerId(developer.getId());
            if (dto.getFollowerType() == null) dto.setFollowerType(DEVELOPER);

            if (!dto.getFollowerType().equals(EntityType.DEVELOPER) || !dto.getFollowerId().equals(developer.getId())) {
                throw new AccessDeniedException("You can only perform the action as an authenticated developer");
            }

        } else if (companyOpt.isPresent()) {
            CompanyEntity company = companyOpt.get();

            if (dto.getFollowerId() == null) dto.setFollowerId(company.getId());
            if (dto.getFollowerType() == null) dto.setFollowerType(COMPANY);

            if (!dto.getFollowerType().equals(EntityType.COMPANY) || !dto.getFollowerId().equals(company.getId())) {
                throw new AccessDeniedException("You can only perform the action as an authenticated company");
            }

        } else {
            throw new AccessDeniedException("Authenticated user not recognized");
        }
    }

    private void completeFollowerFromCredentials(FollowRequestDTO dto) {
        if (dto.getFollowerId() == null || dto.getFollowerType() == null) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            Optional<CompanyEntity> companyOpt = companyRepository.findByCredentials_Email(email);
            if (companyOpt.isPresent()) {
                dto.setFollowerId(companyOpt.get().getId());
                dto.setFollowerType(COMPANY);
                return;
            }

            Optional<DeveloperEntity> developerOpt = developerRepository.findByCredentials_Email(email);
            if (developerOpt.isPresent()) {
                dto.setFollowerId(developerOpt.get().getId());
                dto.setFollowerType(DEVELOPER);
                return;
            }

            throw new IllegalStateException("Authenticated user not found as company or developer");
        }
    }

}

