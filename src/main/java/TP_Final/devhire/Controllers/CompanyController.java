package TP_Final.devhire.Controllers;
import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
//    @PostMapping("/register")
//    public ResponseEntity<?> save(@RequestBody @Valid CompanyEntity company){
//        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.register(company));
//    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CompanyDTO>>> findAll(){
        return ResponseEntity.ok(companyService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CompanyDTO>> findById(@PathVariable  Long id){
        return ResponseEntity.ok(companyService.findById(id));
    }
    @PatchMapping
    public ResponseEntity<EntityModel<CompanyDTO>> updateCompany(@RequestBody CompanyEntity company){
        return ResponseEntity.ok(companyService.updateById(company));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

