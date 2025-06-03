package TP_Final.devhire.Controllers;
import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CompanyDTO>>> findAll(){
        return ResponseEntity.ok(companyService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CompanyDTO>> findById(@PathVariable  Long id){
        return ResponseEntity.ok(companyService.findById(id));
    }
    @GetMapping("/{location}")
    public ResponseEntity<EntityModel<CompanyDTO>>FilterByLocation(@PathVariable String location){
        return ResponseEntity.ok((companyService.FilterByLocation(location)));
    }
    @PatchMapping
    public ResponseEntity<EntityModel<CompanyDTO>> updateCompany(@RequestBody CompanyDTO company){
        return ResponseEntity.ok(companyService.updateById(company));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteByname(@PathVariable String name){
            boolean deleted = companyService.deleteByName(name);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
    }
}

