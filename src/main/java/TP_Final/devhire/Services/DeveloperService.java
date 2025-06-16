package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.DeveloperAssembler;
import TP_Final.devhire.Model.DTOS.*;
import TP_Final.devhire.Model.Entities.AcademicInfo;
import TP_Final.devhire.Model.Entities.DeveloperEntity;
import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Model.Entities.JobExperience;
import TP_Final.devhire.Model.Entities.SkillModel;
import TP_Final.devhire.Model.Enums.HardSkills;
import TP_Final.devhire.Model.Enums.SoftSkills;
import TP_Final.devhire.Model.Mappers.DeveloperMapper;
import TP_Final.devhire.Repositories.DeveloperRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final DeveloperMapper developerMapper;
    private final DeveloperAssembler developerAssembler;

    public DeveloperService(DeveloperRepository developerRepository, DeveloperMapper developerMapper, DeveloperAssembler developerAssembler) {
        this.developerRepository = developerRepository;
        this.developerMapper = developerMapper;
        this.developerAssembler = developerAssembler;
    }

    public EntityModel<DeveloperDTO> register(DeveloperRegisterDTO dto) {
        if (developerRepository.findByCredentials_Email(dto.getEmail()).isPresent()) {
            throw new RuntimeException("The email is already registered");
        }
        if (developerRepository.findByDni(dto.getDni()).isPresent()) {
            throw new RuntimeException("The DNI is already registered");
        }
        DeveloperEntity entity = developerMapper.convertRegisterDTOToEntity(dto);
        entity.setEnabled(true);
        DeveloperEntity saved = developerRepository.save(entity);
        return developerAssembler.toModel(saved);
    }


    public EntityModel<DeveloperDTO> findById (Long id) throws NotFoundException {
        DeveloperEntity user = developerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return developerAssembler.toModel(user);
    }

    public Page<DeveloperEntity> findAllPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return developerRepository.findAll(pageable);
    }

    public List<EntityModel<DeveloperDTO>> findAll() {
        return developerRepository.findAll().stream()
            .map(developerAssembler::toModel)
            .collect(Collectors.toList());
    }

    public void deleteById(Long userID) {
        developerRepository.deleteById(userID);}

    public EntityModel<DeveloperDTO> updateUserFields (Long userID, DeveloperDTO dto) throws NotFoundException {
        verifyAuthenticatedUserMatchesId(userID);
        DeveloperEntity existingUser = developerRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (dto.getAcademicInfo() == null) dto.setAcademicInfo(Collections.emptyList());
        if (dto.getJobExperience() == null) dto.setJobExperience(Collections.emptyList());
        if (dto.getSoftSkills() == null) dto.setSoftSkills(Collections.emptyList());
        if (dto.getHardSkills() == null) dto.setHardSkills(Collections.emptyList());

        if (dto.getName() != null) {
            existingUser.setName(dto.getName());
        }
        if (dto.getLastName() != null) {
            existingUser.setLastName(dto.getLastName());
        }
        if (dto.getDni() != null) {
            existingUser.setDni(dto.getDni());
        }
        if (dto.getLocation() != null) {
            existingUser.setLocation(dto.getLocation());
        }
        if (dto.getSeniority() != null) {
            existingUser.setSeniority(dto.getSeniority());
        }
        if (!dto.getAcademicInfo().isEmpty()) {
            Set<AcademicInfo> current = new HashSet<>(existingUser.getAcademicInfo());
            current.addAll(dto.getAcademicInfo().stream()
                    .map(developerMapper::convertToAcademicInfo)
                    .toList());
            existingUser.setAcademicInfo(new ArrayList<>(current));
        }
        if (!dto.getJobExperience().isEmpty()) {
            Set<JobExperience> current = new HashSet<>(existingUser.getJobExperience());
            current.addAll(dto.getJobExperience().stream()
                    .map(developerMapper::convertToJobExperience)
                    .toList());
            existingUser.setJobExperience(new ArrayList<>(current));
        }
        if (!dto.getSoftSkills().isEmpty()) {
            Set<SoftSkills> current = new HashSet<>(existingUser.getSoftSkills());
            current.addAll(dto.getSoftSkills());
            existingUser.setSoftSkills(new ArrayList<>(current));
        }
        if (!dto.getHardSkills().isEmpty()) {
            Set<HardSkills> current = new HashSet<>(existingUser.getHardSkills());
            current.addAll(dto.getHardSkills());
            existingUser.setHardSkills(new ArrayList<>(current));
        }
        DeveloperEntity saved = developerRepository.save(existingUser);
        return developerAssembler.toModel(saved);
    }

    public EntityModel<DeveloperDTO> updateSkills(Long devId, SkillsDTO skillsDto) throws NotFoundException {
        verifyAuthenticatedUserMatchesId(devId);
        DeveloperEntity developer = developerRepository.findById(devId)
                .orElseThrow(() -> new NotFoundException("Developer no encontrado con ID: " + devId));

        if (skillsDto.getHardSkills() != null) {
            Set<HardSkills> existingHardSkills = new HashSet<>(developer.getHardSkills());
            existingHardSkills.addAll(skillsDto.getHardSkills());
            developer.setHardSkills(new ArrayList<>(existingHardSkills));
        }

        if (skillsDto.getSoftSkills() != null) {
            Set<SoftSkills> existingSoftSkills = new HashSet<>(developer.getSoftSkills());
            existingSoftSkills.addAll(skillsDto.getSoftSkills());
            developer.setSoftSkills(new ArrayList<>(existingSoftSkills));
        }
        developerRepository.save(developer);
        return developerAssembler.toModel(developer);
    }

    public EntityModel<DeveloperDTO> updateAcademicInfo(Long id, List<AcademicInfoDTO> dtos) throws NotFoundException {
        verifyAuthenticatedUserMatchesId(id);
        DeveloperEntity developer = developerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Set<AcademicInfo> current = new HashSet<>(developer.getAcademicInfo());
        current.addAll(dtos.stream()
                .map(developerMapper::convertToAcademicInfo)
                .toList());

        developer.setAcademicInfo(new ArrayList<>(current));
        return developerAssembler.toModel(developerRepository.save(developer));
    }
    public EntityModel<DeveloperDTO> updateJobExperience(Long devId, List<JobExperienceDTO> dtos) throws NotFoundException {
        verifyAuthenticatedUserMatchesId(devId);
        DeveloperEntity developer = developerRepository.findById(devId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Set<JobExperience> current = new HashSet<>(developer.getJobExperience());
        current.addAll(dtos.stream()
                .map(developerMapper::convertToJobExperience)
                .toList());

        developer.setJobExperience(new ArrayList<>(current));
        return developerAssembler.toModel(developerRepository.save(developer));
    }

    public EntityModel<DeveloperDTO> deactivate(Long devId) throws NotFoundException {
        verifyAuthenticatedUserMatchesId(devId);
        DeveloperEntity user = developerRepository.findById(devId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setEnabled(false);
        return developerAssembler.toModel(developerRepository.save(user));
    }

    public EntityModel<DeveloperDTO> reactivate(Long devId) throws NotFoundException {
        verifyAuthenticatedUserMatchesId(devId);
        DeveloperEntity user = developerRepository.findById(devId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setEnabled(true);
        return developerAssembler.toModel(developerRepository.save(user));
    }
    private DeveloperEntity getAuthenticatedDeveloper() throws AccessDeniedException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return developerRepository.findByCredentials_Email(email)
                .orElseThrow(() -> new AccessDeniedException("Authenticated developer not found"));
    }
    private void verifyAuthenticatedUserMatchesId(Long id) throws AccessDeniedException {
        DeveloperEntity authenticatedDev = getAuthenticatedDeveloper();
        if (!authenticatedDev.getId().equals(id)) {
            throw new AccessDeniedException("You can only perform this action on your own profile.");
        }
    }
}
