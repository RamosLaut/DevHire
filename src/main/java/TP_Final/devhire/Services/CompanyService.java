package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.CompanyAssembler;
import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Exceptions.CompanyNotFound;
import TP_Final.devhire.Exceptions.LocationEmptyException;
import TP_Final.devhire.Repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyAssembler companyAssembler;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, CompanyAssembler companyAssembler) {
        this.companyRepository = companyRepository;
        this.companyAssembler = companyAssembler;
    }
    public EntityModel<CompanyDTO> save(CompanyEntity compa){
companyRepository.save(compa);
return companyAssembler.toModel(compa);
    }
    public CollectionModel<EntityModel<CompanyDTO>> findAll(){
        List<EntityModel<CompanyDTO>> companies = companyRepository.findAll().stream()
                .map(companyAssembler::toModel)
                .toList();
        return CollectionModel.of(companies);
    }
    public  EntityModel<CompanyDTO> findById(Long id)throws CompanyNotFound {
        return companyAssembler.toModel(companyRepository.findById(id).orElseThrow(()->new CompanyNotFound("Company not found")));
    }
    public void deleteById(Long id){
        if(companyRepository.findById(id).isEmpty()){
            throw new CompanyNotFound("Company not found");
        }
        companyRepository.deleteById(id);
    }
    public EntityModel<CompanyDTO> FilterByLocation(String location)throws LocationEmptyException {
        return companyAssembler.toModel(companyRepository.FilterByLocation(location).orElseThrow(()->new LocationEmptyException("location empty of companys")));
    }
    public boolean deleteByName(String name) {
        Optional<CompanyEntity> companyOpt = companyRepository.findByName(name);
        if (companyOpt.isPresent()) {
            companyRepository.delete(companyOpt.get());
            return true;
        } else {
            return false;
        }
    }
    public EntityModel<CompanyDTO> updateById(CompanyDTO companyDTO){
        if(companyDTO.getId()==null){
            throw new RuntimeException("Company ID required");
        }
        CompanyEntity companyEntity = companyRepository.findById(companyDTO.getId()).orElseThrow(()->new CompanyNotFound("Company not found"));
        if(companyDTO.getName()!=null){
            companyEntity.setName(companyDTO.getName());
        }else if(companyDTO.getLocation()!=null){
            companyEntity.setLocation(companyDTO.getLocation());
        }else if(companyDTO.getDescription()!=null){
            companyEntity.setDescription(companyDTO.getDescription());
        }
        companyRepository.update(companyEntity.getName(), companyEntity.getLocation(), companyEntity.getDescription(), companyEntity.getId());
        return companyAssembler.toModel(companyEntity);
    }
}



