package TP_Final.devhire.Mappers;
import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Entities.FollowEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {
    @Autowired
    private ModelMapper modelMapper;
}
