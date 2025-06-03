package TP_Final.devhire.Controllers;

import TP_Final.devhire.Assemblers.DeveloperAssembler;
import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Services.DeveloperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/user")
public class DeveloperController {
    private final DeveloperService developerService;
    private final DeveloperAssembler developerAssembler;

    @Autowired
    public DeveloperController(DeveloperService developerService, DeveloperAssembler developerAssembler) {
        this.developerService = developerService;
        this.developerAssembler = developerAssembler;
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<DeveloperDTO>> register(@RequestBody @Valid DeveloperRegisterDTO dto) {
        return ResponseEntity.ok(developerService.register(dto));

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> getDevById(@PathVariable Long id){
        return ResponseEntity.ok(developerService.findById(id));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DeveloperDTO>>> listAllDevs() {
        List<EntityModel<DeveloperDTO>> users = developerService.findAll();
       return ResponseEntity.ok(
                CollectionModel.of(users,
                        linkTo(methodOn(DeveloperController.class).listAllDevs()).withSelfRel())
        );
    }

    @GetMapping("/page")
    public ResponseEntity<CollectionModel<EntityModel<DeveloperDTO>>> listAllDevsPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    Page<EntityModel<DeveloperDTO>> userPage = developerService.findAllPage(page, size);
        return ResponseEntity.ok(
            CollectionModel.of(userPage.getContent(),
                    linkTo(methodOn(DeveloperController.class).listAllDevsPage(page, size)).withSelfRel())
        );
    }

    @PutMapping("/academicInfo/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> updateAcademicInfo(@PathVariable Long id, @RequestBody List<AcademicInfoDTO> academicInfoDTOS) {
        return ResponseEntity.ok(developerService.updateAcademicInfo(id, academicInfoDTOS));
    }


    @PutMapping("/jobExperience/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> updateJobExperience(@PathVariable Long id, @RequestBody List<JobExperienceDTO> jobExperienceDTOS) {
        return ResponseEntity.ok(developerService.updateJobExperience(id, jobExperienceDTOS));
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> updateSkills(@PathVariable Long id, @RequestBody SkillsDTO skillsDTO) {
        return ResponseEntity.ok(developerService.updateSkills(id, skillsDTO));
    }
//
//    @PutMapping("/password/{id}")
//    public ResponseEntity<EntityModel<DeveloperDTO>> updatePassword(@PathVariable Long id, @RequestBody @Valid DeveloperPasswordDTO dto) {
//        return ResponseEntity.ok(developerService.updatePassword(id, dto));
//    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> updateDev(@PathVariable Long id, @RequestBody DeveloperDTO dto) {
        return ResponseEntity.ok(developerAssembler.toModel(developerService.updateUserFields(id, dto)));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> logicDown(@PathVariable Long id) {
        return ResponseEntity.ok(developerService.deactivate(id));
    }

    @PutMapping("/reactivate/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> logicHigh(@PathVariable Long id) {
        return ResponseEntity.ok(developerService.reactivate(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDev(@PathVariable Long id) {
        developerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
