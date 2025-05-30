package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.CompanyAssembler;
import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Exceptions.CompanyAlreadyExistsException;
import TP_Final.devhire.Exceptions.CompanyNotFound;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Security.Entities.CredentialsEntity;
import TP_Final.devhire.Security.Repositories.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyAssembler companyAssembler;
    private final PasswordEncoder passwordEncoder;
    private final CredentialsRepository credentialsRepository;
    @Autowired
    public CompanyService(CompanyRepository companyRepository, CompanyAssembler companyAssembler, PasswordEncoder passwordEncoder, CredentialsRepository credentialsRepository) {
        this.companyRepository = companyRepository;
        this.companyAssembler = companyAssembler;
        this.passwordEncoder = passwordEncoder;
        this.credentialsRepository = credentialsRepository;
    }
    public EntityModel<CompanyDTO> register(CompanyEntity company){
        companyRepository.save(company);
        CredentialsEntity credentialsEntity = CredentialsEntity.builder()
                .email(company.getEmail())
                .password(passwordEncoder.encode("password"))
                .company(company)
                .build();
        credentialsRepository.save(credentialsEntity);
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



