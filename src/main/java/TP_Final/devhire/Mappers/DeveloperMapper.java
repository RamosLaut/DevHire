package TP_Final.devhire.Mappers;

import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Entities.AcademicInfo;
import TP_Final.devhire.Entities.JobExperience;
import TP_Final.devhire.Entities.DeveloperEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeveloperMapper {
    @Autowired
    private ModelMapper modelMapper;

    //  DTO to Entity
    public DeveloperEntity convertToEntity(DeveloperDTO dto) {
        DeveloperEntity entity = modelMapper.map(dto, DeveloperEntity.class);

        entity.setAcademicInfo(convertToAcademicInfoList(dto.getAcademicInfo()));
        entity.setJobExperience(convertToJobExperienceList(dto.getJobExperience()));

        return entity;
    }

    public DeveloperEntity convertRegisterDTOToEntity(DeveloperRegisterDTO dto){
        return modelMapper.map(dto, DeveloperEntity.class);
    }

    public DeveloperEntity convertUpdateDTOToEntity(DeveloperUpdateDTO dto){
        return modelMapper.map(dto, DeveloperEntity.class);
    }

    public AcademicInfo convertToAcademicInfo(AcademicInfoDTO dto) {
        return modelMapper.map(dto, AcademicInfo.class);
    }

    public DeveloperEntity convertCredentialDTOToEntity(DeveloperPasswordDTO dto){
        return modelMapper.map(dto, DeveloperEntity.class);
    }

    public JobExperience convertToJobExperience(JobExperienceDTO dto) {
        return modelMapper.map(dto, JobExperience.class);
    }

    // Entity to DTO
    public DeveloperDTO convertToDto(DeveloperEntity user){
        DeveloperDTO dto = modelMapper.map(user, DeveloperDTO.class);
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

    public DeveloperRegisterDTO convertToRegisterDTO(DeveloperEntity user){
        return modelMapper.map(user, DeveloperRegisterDTO.class);
    }
    public DeveloperPasswordDTO convertToCredentialsDTO(DeveloperEntity user){
        return modelMapper.map(user, DeveloperPasswordDTO.class);
    }
    public DeveloperUpdateDTO convertToUpdateDTO(DeveloperEntity user){
        return modelMapper.map(user, DeveloperUpdateDTO.class);
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
    public DeveloperApplicantDTO convertToApplicantDTO(DeveloperEntity dev){
        return modelMapper.map(dev, DeveloperApplicantDTO.class);
    }
}
