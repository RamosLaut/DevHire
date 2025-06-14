package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.DeveloperAssembler;
import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Entities.DeveloperEntity;
import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Mappers.DeveloperMapper;
import TP_Final.devhire.Repositories.DeveloperRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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


    public EntityModel<DeveloperDTO> findById(Long id) {
        DeveloperEntity user = developerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return developerAssembler.toModel(user);
    }

    public Page<EntityModel<DeveloperDTO>> findAllPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return developerRepository.findAll(pageable)
                .map(developerAssembler::toModel);
    }

    public List<EntityModel<DeveloperDTO>> findAll() {
        return developerRepository.findAll().stream()
            .map(developerAssembler::toModel)
            .collect(Collectors.toList());
    }

    public void deleteById(Long userID) {
        developerRepository.deleteById(userID);}

    public DeveloperEntity updateUserFields (Long userID, DeveloperDTO dto){
        DeveloperEntity existingUser = developerRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("User not found"));

        DeveloperEntity updatedData = developerMapper.convertToEntity(dto);

        existingUser.setName(updatedData.getName());
        existingUser.setLastName(updatedData.getLastName());
//        existingUser.setEmail(updatedData.getEmail());
        existingUser.setDni(updatedData.getDni());
//        existingUser.setPassword(updatedData.getPassword());
        existingUser.setAcademicInfo(updatedData.getAcademicInfo());
        existingUser.setLocation(updatedData.getLocation());
        existingUser.setSeniority(updatedData.getSeniority());
        existingUser.setJobExperience(updatedData.getJobExperience());
        existingUser.setSoftSkills(updatedData.getSoftSkills());
        existingUser.setHardSkills(updatedData.getHardSkills());

        return developerRepository.save(existingUser);
    }

//    public EntityModel<DeveloperDTO> updatePassword(Long userID, DeveloperPasswordDTO dto) {
//        DeveloperEntity user = developerRepository.findById(userID)
//                .orElseThrow(() -> new DeveloperNotFoundException("User not found"));
//        user.setPassword(dto.getPassword());
//        return developerAssembler.toModel(developerRepository.save(user));
//    }

    public EntityModel<DeveloperDTO> updateSkills(Long userID, SkillsDTO dto) {
        DeveloperEntity user = developerRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setSoftSkills(dto.getSoftSkills());
        user.setHardSkills(dto.getHardSkills());
        return developerAssembler.toModel(developerRepository.save(user));
    }

    public EntityModel<DeveloperDTO> updateAcademicInfo(Long id, List<AcademicInfoDTO> dtos) {
        DeveloperEntity user = developerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setAcademicInfo(dtos.stream()
                .map(developerMapper::convertToAcademicInfo)
                .collect(Collectors.toList()));
        return developerAssembler.toModel(developerRepository.save(user));
    }
    public EntityModel<DeveloperDTO> updateJobExperience(Long userID, List<JobExperienceDTO> dtos) {
        DeveloperEntity user = developerRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setJobExperience(dtos.stream()
                .map(developerMapper::convertToJobExperience)
                .collect(Collectors.toList()));
        return developerAssembler.toModel(developerRepository.save(user));
    }

    public EntityModel<DeveloperDTO> deactivate(Long userID) {
        DeveloperEntity user = developerRepository.findById(userID).orElseThrow(() -> new NotFoundException("User not found"));
        user.setEnabled(false);
        return developerAssembler.toModel(developerRepository.save(user));
    }

    public EntityModel<DeveloperDTO> reactivate(Long userID) {
        DeveloperEntity user = developerRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setEnabled(true);
        return developerAssembler.toModel(developerRepository.save(user));
    }


}
