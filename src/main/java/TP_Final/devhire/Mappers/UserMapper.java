package TP_Final.devhire.Mappers;

import TP_Final.devhire.DTOS.AcademicInfoDTO;
import TP_Final.devhire.DTOS.JobExperienceDTO;
import TP_Final.devhire.DTOS.UserDTO;
import TP_Final.devhire.DTOS.UserRegisterDTO;
import TP_Final.devhire.Entities.AcademicInfo;
import TP_Final.devhire.Entities.JobExperience;
import TP_Final.devhire.Entities.UserEntity;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;
    public UserDTO converToDto(UserEntity user){
        return modelMapper.map(user, UserDTO.class);
    }
    public UserEntity converToEntity(UserDTO userDto){
        return modelMapper.map(userDto, UserEntity.class);
    }

    public UserEntity convertToEntity(UserRegisterDTO dto){
        return modelMapper.map(dto, UserEntity.class);
    }

    public AcademicInfo convertToAcademicInfo(AcademicInfoDTO dto) {
        return modelMapper.map(dto, AcademicInfo.class);
    }

    public AcademicInfoDTO convertToAcademicInfoDTO(AcademicInfo entity) {
        return modelMapper.map(entity, AcademicInfoDTO.class);
    }

    public JobExperience convertToJobExperience(JobExperienceDTO dto) {
        return modelMapper.map(dto, JobExperience.class);
    }

    public JobExperienceDTO convertToJobExperienceDTO(JobExperience entity) {
        return modelMapper.map(entity, JobExperienceDTO.class);
    }

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
}
