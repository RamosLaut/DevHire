package TP_Final.devhire.Services;

import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Repositories.PublicationsRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@NoArgsConstructor
public class PublicationService{
    @Autowired
    private PublicationsRepository publicationsRepository;

    public List<PublicationEntity> findAll(){
        return publicationsRepository.findAll();
    }

}
