package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.CompanyAssembler;
import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Exceptions.CompanyAlreadyExistsException;
import TP_Final.devhire.Exceptions.CompanyNotFound;
import TP_Final.devhire.Repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyAssembler companyAssembler;
    @Autowired
    public CompanyService(CompanyRepository companyRepository, CompanyAssembler companyAssembler) {
        this.companyRepository = companyRepository;
        this.companyAssembler = companyAssembler;
    }
    public EntityModel<CompanyDTO> save(CompanyEntity company)throws CompanyAlreadyExistsException {
        boolean exists = companyRepository.findAll().stream()
                .map(CompanyEntity::getName)
                .anyMatch(name -> name.equalsIgnoreCase(company.getName()));
        if(exists) {
            throw new CompanyAlreadyExistsException("Company with name " + company.getName() + " already exists.");
        }
        companyRepository.save(company);
        return companyAssembler.toModel(company);
    }
    public CollectionModel<EntityModel<CompanyDTO>> findAll(){
        List<EntityModel<CompanyDTO>> companys = companyRepository.findAll().stream()
                .map(companyAssembler::toModel)
                .toList();
        return CollectionModel.of(companys);
    }
    public  EntityModel<CompanyDTO> findById(Long id)throws CompanyNotFound {
        return companyAssembler.toModel(companyRepository.findById(id).orElseThrow(()->new CompanyNotFound("Company not found")));
    }
    public void deleteById(Long id){
        companyRepository.deleteById(id);
    }
    public EntityModel<CompanyDTO> updateById(CompanyEntity company){
        companyRepository.update(company.getName(), company.getLocation(), company.getDescription(), company.getId());
        return companyAssembler.toModel(company);
    }
}



