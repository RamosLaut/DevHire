package TP_Final.devhire.Controllers;

import TP_Final.devhire.DTOS.ApplicationDTO;
import TP_Final.devhire.DTOS.JobDTO;
import TP_Final.devhire.Entities.JobEntity;
import TP_Final.devhire.Services.ApplicationService;
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
    @PostMapping
    public ResponseEntity<?> save(@RequestBody JobEntity job){
        jobService.save(job);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    @PostMapping("/{jobId}/apply")
    public ResponseEntity<EntityModel<ApplicationDTO>> apply(@PathVariable Long jobId){
        return ResponseEntity.ok(applicationService.apply(jobId));
    }


}
