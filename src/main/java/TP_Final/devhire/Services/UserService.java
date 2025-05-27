package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.UserAssembler;
import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Entities.AcademicInfo;
import TP_Final.devhire.Entities.JobExperience;
import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.SoftSkills;
import TP_Final.devhire.Exceptions.UserNotFoundException;
import TP_Final.devhire.Mappers.UserMapper;
import TP_Final.devhire.Repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserAssembler userAssembler;

    public UserService(UserRepository userRepository, UserMapper userMapper, UserAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userAssembler = userAssembler;
    }


    public EntityModel<UserDTO> register(UserRegisterDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("The username already exists");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("The email is already registered");
        }
        if (userRepository.findByDni(dto.getDni()).isPresent()) {
            throw new RuntimeException("The DNI is already registered");
        }
        UserEntity entity = userMapper.convertRegisterDTOToEntity(dto);
        entity.setState(true);
        UserEntity saved = userRepository.save(entity);
        return userAssembler.toModel(saved);
    }


    public EntityModel<UserDTO> findById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return userAssembler.toModel(user);
    }

    public Page<EntityModel<UserDTO>> findAllPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable)
                .map(userAssembler::toModel);
    }

    public List<EntityModel<UserDTO>> findAll() {
        return userRepository.findAll().stream()
            .map(userAssembler::toModel)
            .collect(Collectors.toList());
    }

    public void deleteById(Long userID) {userRepository.deleteById(userID);}

    public UserEntity updateUserFields (Long userID, UserDTO dto){
        UserEntity existingUser = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserEntity updatedData = userMapper.convertToEntity(dto);

        existingUser.setName(updatedData.getName());
        existingUser.setLastName(updatedData.getLastName());
        existingUser.setEmail(updatedData.getEmail());
        existingUser.setDni(updatedData.getDni());
        existingUser.setPassword(updatedData.getPassword());
        existingUser.setAcademicInfo(updatedData.getAcademicInfo());
        existingUser.setLocation(updatedData.getLocation());
        existingUser.setSeniority(updatedData.getSeniority());
        existingUser.setJobExperience(updatedData.getJobExperience());
        existingUser.setSoftSkills(updatedData.getSoftSkills());
        existingUser.setHardSkills(updatedData.getHardSkills());

        return userRepository.save(existingUser);
    }

    public EntityModel<UserDTO> updatePassword(Long userID, UserPasswordDTO dto) {
        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setPassword(dto.getPassword());
        return userAssembler.toModel(userRepository.save(user));
    }

    public EntityModel<UserDTO> updateSkills(Long userID, SkillsDTO dto) {
        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setSoftSkills(dto.getSoftSkills());
        user.setHardSkills(dto.getHardSkills());
        return userAssembler.toModel(userRepository.save(user));
    }

    public EntityModel<UserDTO> updateAcademicInfo(Long id, List<AcademicInfoDTO> dtos) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setAcademicInfo(dtos.stream()
                .map(userMapper::convertToAcademicInfo)
                .collect(Collectors.toList()));
        return userAssembler.toModel(userRepository.save(user));
    }
    public EntityModel<UserDTO> updateJobExperience(Long userID, List<JobExperienceDTO> dtos) {
        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setJobExperience(dtos.stream()
                .map(userMapper::convertToJobExperience)
                .collect(Collectors.toList()));
        return userAssembler.toModel(userRepository.save(user));
    }

    public EntityModel<UserDTO> deactivate(Long userID) {
        UserEntity user = userRepository.findById(userID).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setState(false);
        return userAssembler.toModel(userRepository.save(user));
    }

    public EntityModel<UserDTO> reactivate(Long userID) {
        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setState(true);
        return userAssembler.toModel(userRepository.save(user));
    }


}
