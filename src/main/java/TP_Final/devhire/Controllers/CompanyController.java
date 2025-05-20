package TP_Final.devhire.Controllers;

import TP_Final.devhire.Assemblers.CompanyAssembler;
import TP_Final.devhire.DTOS.CommentDTO;
import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyAssembler companyAssembler;
        @Autowired

    public CompanyController(CompanyService companyService, CompanyAssembler companyAssembler) {
        this.companyService = companyService;
        this.companyAssembler = companyAssembler;
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody CompanyEntity company){
        companyService.save(company);
        return ResponseEntity.ok(companyAssembler.toModel(company));
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CompanyDTO>>> findAll(){
        List<EntityModel<CompanyDTO >> companys = companyService.findAll().stream().map(companyAssembler::toModel).toList();
        return ResponseEntity.ok(CollectionModel.of(companys));
}
 @GetMapping
     public  ResponseEntity<EntityModel<CompanyDTO>> findById(@PathVariable  Long idx){
     return ResponseEntity.ok(companyAssembler.toModel(companyService.findById(idx)));

}
    @PatchMapping
    public ResponseEntity<EntityModel<CompanyDTO>>UpdateCompany(@RequestBody CompanyEntity company){
        companyService.UpdateXId(company);
        return ResponseEntity.ok(companyAssembler.toModel(company));
    }

@DeleteMapping
    public ResponseEntity<?> DeleteById(@PathVariable long id){
            companyService.deleteById(id);
            return ResponseEntity.noContent().build();
}
}

