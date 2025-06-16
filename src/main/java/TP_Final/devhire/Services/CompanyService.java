package TP_Final.devhire.Services;
import TP_Final.devhire.Assemblers.CompanyAssembler;
import TP_Final.devhire.Model.DTOS.CompanyDTO;
import TP_Final.devhire.Model.Entities.CompanyEntity;
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
import java.util.stream.Stream;

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
        if(companyRepository.findAll().isEmpty()){
            throw new NotFoundException("No companies found");
        }
        List<CompanyEntity> companies = companyRepository.findAll();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            EntityModel<CompanyDTO> ownCompanyModel = companyAssembler.toOwnCompanyModel(company);
            List<EntityModel<CompanyDTO>> otherCompanies = companies.stream()
                    .filter(companyEntity -> !companyEntity.equals(company))
                    .map(companyAssembler::toModel)
                    .toList();
            List<EntityModel<CompanyDTO>> allCompanies = Stream.concat(otherCompanies.stream(), Stream.of(ownCompanyModel)).toList();
            if(allCompanies.isEmpty()){
                throw new NotFoundException("No companies found");
            }
            return CollectionModel.of(allCompanies);
        }else {
            List<EntityModel<CompanyDTO>> companiesModels = companies.stream()
                    .map(companyAssembler::toModel)
                    .toList();
            if(companiesModels.isEmpty()){
                throw new NotFoundException("No companies found");
            }
            return CollectionModel.of(companiesModels);
        }
    }
    public EntityModel<CompanyDTO> findById(Long id)throws NotFoundException {
        if(companyRepository.findById(id).isEmpty()){
            throw new NotFoundException("Company not found");
        }
        CompanyEntity company = companyRepository.findById(id).get();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity ownCompany = companyRepository.findByCredentials_Email(email).get();
            if(ownCompany.equals(company)){
                return companyAssembler.toOwnCompanyModel(company);
            }else return companyAssembler.toModel(company);
        }else return companyAssembler.toModel(company);
    }
    public String findCompaniesQuantity(){
        long companiesQuantity = companyRepository.findAll().size();
        return "Quantity of companies: " + companiesQuantity + ".";
    }
    public CollectionModel<EntityModel<CompanyDTO>> findByLocation(String location)throws LocationEmptyException {
        List<CompanyEntity> companies = companyRepository.findAll().stream()
                .filter(company -> location.equals(company.getLocation()))
                .toList();
        if(companies.isEmpty()){
            throw new LocationEmptyException("There are no companies in this location: " + location + ".");
        }
        return CollectionModel.of(companies.stream().map(companyAssembler::toModel).toList());
    }
    public CollectionModel<EntityModel<CompanyDTO>> findByName(String name)throws NotFoundException {
        List<CompanyEntity> companies = companyRepository.findByName(name).stream()
                .toList();
        if(companies.isEmpty()){
            throw new NotFoundException("Company not found");
        }
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
    public int companiesQuantity(){
        return companyRepository.findAll().size();
    }
}



