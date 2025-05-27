package TP_Final.devhire.Mappers;

import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Entities.AcademicInfo;
import TP_Final.devhire.Entities.JobExperience;
import TP_Final.devhire.Entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    //  DTO to Entity
    public UserEntity convertToEntity(UserDTO dto) {
        UserEntity entity = modelMapper.map(dto, UserEntity.class);

        entity.setAcademicInfo(convertToAcademicInfoList(dto.getAcademicInfo()));
        entity.setJobExperience(convertToJobExperienceList(dto.getJobExperience()));

        return entity;
    }

    public UserEntity convertRegisterDTOToEntity(UserRegisterDTO dto){
        return modelMapper.map(dto, UserEntity.class);
    }

    public UserEntity convertUpdateDTOToEntity(UserUpdateDTO dto){
        return modelMapper.map(dto, UserEntity.class);
    }

    public AcademicInfo convertToAcademicInfo(AcademicInfoDTO dto) {
        return modelMapper.map(dto, AcademicInfo.class);
    }

    public UserEntity convertCredentialDTOToEntity(UserPasswordDTO dto){
        return modelMapper.map(dto, UserEntity.class);
    }

    public JobExperience convertToJobExperience(JobExperienceDTO dto) {
        return modelMapper.map(dto, JobExperience.class);
    }

    // Entity to DTO
    public UserDTO convertToDto(UserEntity user){
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        dto.setAcademicInfo(convertToAcademicInfoDTOList(user.getAcademicInfo()));
        dto.setJobExperience(convertToJobExperienceDTOList(user.getJobExperience()));
        return dto;
    }

    public AcademicInfoDTO convertToAcademicInfoDTO(AcademicInfo entity) {
        return modelMapper.map(entity, AcademicInfoDTO.class);
    }

    public JobExperienceDTO convertToJobExperienceDTO(JobExperience entity) {
        return modelMapper.map(entity, JobExperienceDTO.class);
    }

    public UserRegisterDTO convertToRegisterDTO(UserEntity user){
        return modelMapper.map(user, UserRegisterDTO.class);
    }
    public UserPasswordDTO convertToCredentialsDTO(UserEntity user){
        return modelMapper.map(user, UserPasswordDTO.class);
    }
    public UserUpdateDTO convertToUpdateDTO(UserEntity user){
        return modelMapper.map(user, UserUpdateDTO.class);
    }

    // List
    public List<AcademicInfo> convertToAcademicInfoList(List<AcademicInfoDTO> dtos) {
        return dtos.stream()
                .map(this::convertToAcademicInfo)
                .toList();
    }

    public List<JobExperience> convertToJobExperienceList(List<JobExperienceDTO> dtos) {
        return dtos.stream()
                .map(this::convertToJobExperience)
                .toList();
    }

    public List<AcademicInfoDTO> convertToAcademicInfoDTOList(List<AcademicInfo> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
//        return entities.stream()
//                .map(this::convertToAcademicInfoDTO)
//                .toList();
        return entities.stream()
                .map(this::convertToAcademicInfoDTO)
                .collect(Collectors.toList());
    }

    public List<JobExperienceDTO> convertToJobExperienceDTOList(List<JobExperience> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::convertToJobExperienceDTO)
                .toList();
    }

}
