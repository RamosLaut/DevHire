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
    public ResponseEntity<EntityModel<CompanyDTO>> findById(@PathVariable Long id){
        return ResponseEntity.ok(companyService.findById(id));
    }
    @GetMapping("/filter/{location}")
    public ResponseEntity<CollectionModel<EntityModel<CompanyDTO>>>findByLocation(@PathVariable String location){
        return ResponseEntity.ok((companyService.findByLocation(location)));
    }
    @PatchMapping
    public ResponseEntity<EntityModel<CompanyDTO>> updateOwnCompany(@RequestBody CompanyDTO company){
        return ResponseEntity.ok(companyService.update(company));
    }
    @DeleteMapping()
    public ResponseEntity<?> deleteOwnCompany(){
        companyService.deleteOwnAccount();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name){
            boolean deleted = companyService.deleteByName(name);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
    }
}

