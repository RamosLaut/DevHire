package TP_Final.devhire.Controllers;

import TP_Final.devhire.DTOS.ApplicationDTO;
import TP_Final.devhire.DTOS.DeveloperDTO;
import TP_Final.devhire.Services.ApplicationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
public class ApplicationController {
    private final ApplicationService applicationService;
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(applicationService.findAll());
    }
    @GetMapping("/ownApplications")
    public ResponseEntity<?> findOwnApplications(){
        return ResponseEntity.ok(applicationService.findOwnApplications());
    }
    @GetMapping("/{applicationId}")
    public ResponseEntity<?> findById(@PathVariable Long applicationId){
        return ResponseEntity.ok(applicationService.findById(applicationId));
    }
    @GetMapping("/applicants/{jobId}")
    public ResponseEntity<CollectionModel<EntityModel<ApplicationDTO>>> getApplicantsByJobId(@PathVariable Long jobId){
        return ResponseEntity.ok(applicationService.findApplicantsByJobId(jobId));
    }
    @DeleteMapping("/delete/{applicationId}")
    public ResponseEntity<?> deleteOwnApplication(@PathVariable Long applicationId){
        applicationService.deleteApplicationById(applicationId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/applicantsWithAllHardRequirements/{jobId}")
    public ResponseEntity<CollectionModel<EntityModel<DeveloperDTO>>> getApplicantsWithAllHardRequirements(@PathVariable Long jobId){
        return ResponseEntity.ok(applicationService.findApplicantsWithAllHardRequirements(jobId));
    }
    @GetMapping("applicantsWithAnyHardRequirement/{jobId}")
    public ResponseEntity<CollectionModel<EntityModel<DeveloperDTO>>> getApplicantsWithAnyHardRequirement(@PathVariable Long jobId){
        return ResponseEntity.ok(applicationService.findApplicantsWithAnyHardRequirements(jobId));
    }
    @GetMapping("applicantsWithMinHardRequirement/{jobId}")
    public ResponseEntity<CollectionModel<EntityModel<DeveloperDTO>>> getApplicantsWithMinHardRequirement(@PathVariable Long jobId, @RequestParam int min){
        return ResponseEntity.ok(applicationService.findApplicantsWithMinHardRequirements(jobId, min));
    }

}
