package TP_Final.devhire.Controllers;

import TP_Final.devhire.DTOS.ApplicationDTO;
import TP_Final.devhire.DTOS.JobDTO;

import TP_Final.devhire.Entities.JobEntity;
import TP_Final.devhire.Services.ApplicationService;

import TP_Final.devhire.Entities.SkillModel;

import TP_Final.devhire.Services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {
    private final JobService jobService;
    private final ApplicationService applicationService;
    @Autowired
    public JobController(JobService jobService, ApplicationService applicationService) {
        this.jobService = jobService;
        this.applicationService = applicationService;
    }
    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody JobDTO jobDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.save(jobDTO));
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }
    @GetMapping("/{jobId}")
    public ResponseEntity<?>findById(@PathVariable long jobId){
        return ResponseEntity.ok(jobService.findById(jobId));
    }
    @GetMapping("/{jobId}/requirements")
    public ResponseEntity<List<String>>findJobOfferRequirements(@PathVariable long jobId){
        return ResponseEntity.ok(jobService.findJobRequirements(jobId));
    }
    @GetMapping("/ownOffers")
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> findOwnOffers(){
        return ResponseEntity.ok(jobService.findOwnOffers());
    }
    @GetMapping("/{jobId}/findAvailableSkills")
    public ResponseEntity<CollectionModel<SkillModel>> findAvailableSkills(@PathVariable long jobId){
        return ResponseEntity.ok(jobService.getSkills(jobId));
    }
    @PostMapping("/{id}/addRequirement")
    public ResponseEntity<?> addRequirement(
            @PathVariable long id,
            @RequestParam String skill
    ) {
        jobService.addRequirement(id, skill);
        return ResponseEntity.ok("Requirement added");
    }
    @GetMapping("/findBySkill/{skill}")
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> findBySkill(@PathVariable String skill){
        return ResponseEntity.ok(jobService.findBySkill(skill));
    }
    @GetMapping("findByCompany/{companyName}")
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> findByCompany(@PathVariable String companyName){
        return ResponseEntity.ok(jobService.findByCompanyName(companyName));
    }
    @PatchMapping("/update/{jobId}")
    public ResponseEntity<?> updateJobOffer(@PathVariable long jobId, @RequestBody JobDTO job){
        return ResponseEntity.ok(jobService.update(jobId, job));
    }
    @DeleteMapping("/delete/{jobId}")
    public ResponseEntity<?> deleteJobOffer(@PathVariable long jobId){
        jobService.deleteById(jobId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{jobId}/apply")
    public ResponseEntity<EntityModel<ApplicationDTO>> apply(@PathVariable Long jobId){
        return ResponseEntity.ok(applicationService.apply(jobId));
    }


}
