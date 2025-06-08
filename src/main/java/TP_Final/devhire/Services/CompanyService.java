package TP_Final.devhire.Services;
import TP_Final.devhire.Assemblers.CompanyAssembler;
import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Exceptions.LocationEmptyException;
import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Exceptions.UnauthorizedException;
import TP_Final.devhire.Repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public CollectionModel<EntityModel<CompanyDTO>> findAll(){
        List<EntityModel<CompanyDTO>> companies = companyRepository.findAll().stream()
                .filter(CompanyEntity::getEnabled)
                .map(companyAssembler::toModel)
                .toList();
        return CollectionModel.of(companies);
    }
    public EntityModel<CompanyDTO> findById(Long id)throws NotFoundException {
        return companyAssembler.toModel(companyRepository.findById(id).orElseThrow(()->new NotFoundException("Company not found")));
    }
    public String findCompaniesQuantity(){
        long companiesQuantity = companyRepository.findAll().size();
        return "Quantity of companies: " + companiesQuantity + ".";
    }
    public CollectionModel<EntityModel<CompanyDTO>> findByLocation(String location)throws LocationEmptyException {
        List<CompanyEntity> companies = companyRepository.findAll().stream()
                .filter(company -> company.getLocation().equalsIgnoreCase(location))
                .toList();
        return CollectionModel.of(companies.stream().map(companyAssembler::toModel).toList());
    }
    public EntityModel<CompanyDTO> update(CompanyDTO companyDTO)throws UnauthorizedException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CompanyEntity company = companyRepository.findByCredentials_Email(email).orElseThrow(()->new UnauthorizedException("You don't have permission to update this account"));
        if(companyDTO.getName()!=null){
            company.setName(companyDTO.getName());
        }if(companyDTO.getLocation()!=null){
            company.setLocation(companyDTO.getLocation());
        }if(companyDTO.getDescription()!=null){
            company.setDescription(companyDTO.getDescription());
        }
        companyRepository.update(company.getName(), company.getLocation(), company.getDescription(), company.getId());
        return companyAssembler.toModel(company);
    }
    public void deleteOwnAccount()throws UnauthorizedException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CompanyEntity company = companyRepository.findByCredentials_Email(email).orElseThrow(()->new UnauthorizedException("You don't have permission to delete this account"));
        companyRepository.logicDown(company.getId());
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
}



