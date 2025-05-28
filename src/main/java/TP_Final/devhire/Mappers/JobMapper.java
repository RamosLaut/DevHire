package TP_Final.devhire.Mappers;

import TP_Final.devhire.DTOS.JobDTO;
import TP_Final.devhire.Entities.JobEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    @Autowired
    private ModelMapper mapper;
    public JobDTO convertToDTO(JobEntity job){
        return mapper.map(job,JobDTO.class);
    }
    public JobEntity convertToEntity(JobDTO jobDTO){
        return mapper.map(jobDTO,JobEntity.class);
    }
}
