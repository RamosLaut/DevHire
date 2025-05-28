package TP_Final.devhire.Controllers;

import TP_Final.devhire.DTOS.JobDTO;
import TP_Final.devhire.Entities.JobEntity;
import TP_Final.devhire.Services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {
    private final JobService jobService;
    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody JobEntity job){
        jobService.save(job);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }
    @GetMapping("/{jobId}")
    public ResponseEntity<?>findById(@PathVariable long jobId){
        return ResponseEntity.ok(jobService.findById(jobId));
    }
    @GetMapping("/ownOffers")
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> findOwnOffers(){
        return ResponseEntity.ok(jobService.findOwnOffers());
    }
    @GetMapping("/{jobId}/findRequirements")
    public ResponseEntity<List<String>> findJobRequirements(@PathVariable long jobId){
        return ResponseEntity.ok(jobService.getRequirements(jobId));
    }
//    @PatchMapping("/{jobId}/addRequirement/{requirement}")
//    public ResponseEntity<?> addHardSkill(@PathVariable String requirement, @PathVariable long jobId){
//        jobService.addRequirementHardSkill(jobId, requirement);
//        return ResponseEntity.noContent().build();
//    }
}
