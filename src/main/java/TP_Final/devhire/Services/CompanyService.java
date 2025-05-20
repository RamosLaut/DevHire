package TP_Final.devhire.Services;

import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Exceptions.CommentNotFoundException;
import TP_Final.devhire.Repositories.CommentRepository;
import TP_Final.devhire.Repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    public void save(CompanyEntity company){
        if(companyRepository.findAll().contains(company)){
            System.out.println("The company exists ");
        }else {
            companyRepository.save(company);
        }
    }
    public Optional deleteById(Long idToDelete){
        Optional<CompanyEntity> companyToDelete = companyRepository.findById(Long.valueOf(idToDelete));

        if (companyToDelete.isPresent()) {
            companyRepository.deleteById(Long.valueOf(idToDelete));
            return companyToDelete;
        } else {
            return Optional.empty();
        }
    }
    public List<CompanyEntity> findAll(){
        return companyRepository.findAll();
    }
public  CompanyEntity findById(Long id){
    Optional<CompanyEntity> company = companyRepository.findById(Long.valueOf(id));
    return company.orElseThrow(()->new CommentNotFoundException("Comment not found"));

}

    public void UpdateXId(CompanyEntity company){
        if(companyRepository.existsById(company.getCompany_id())){
            //System.out.println("The company not exists  so now gonna create a company with new information");
            companyRepository.save(company);

        }
    }
}



