package TP_Final.devhire.Controllers;

import TP_Final.devhire.Assemblers.UserAssembler;
import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserAssembler userAssembler;

    @Autowired
    public UserController(UserService userService, UserAssembler userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<UserDTO>> register(@RequestBody @Valid UserRegisterDTO dto) {
        return ResponseEntity.ok(userService.register(dto));

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> listAllUsers() {
        List<EntityModel<UserDTO>> users = userService.findAll();
       return ResponseEntity.ok(
                CollectionModel.of(users,
                        linkTo(methodOn(UserController.class).listAllUsers()).withSelfRel())
        );
    }

    @GetMapping("/page")
    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> listAllUsersPage(@RequestParam(defaultValue = "0") int page,  @RequestParam(defaultValue = "10") int size) {
    Page<EntityModel<UserDTO>> userPage = userService.findAllPage(page, size);
        return ResponseEntity.ok(
            CollectionModel.of(userPage.getContent(),
                    linkTo(methodOn(UserController.class).listAllUsersPage(page, size)).withSelfRel())
        );
    }

    @PutMapping("/academicInfo/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateAcademicInfo(@PathVariable Long id, @RequestBody List<AcademicInfoDTO> academicInfoDTOS) {
        return ResponseEntity.ok(userService.updateAcademicInfo(id, academicInfoDTOS));
    }


    @PutMapping("/jobExperience/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateJobExperience(@PathVariable Long id, @RequestBody List<JobExperienceDTO> jobExperienceDTOS) {
        return ResponseEntity.ok(userService.updateJobExperience(id, jobExperienceDTOS));
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateSkills(@PathVariable Long id, @RequestBody SkillsDTO skillsDTO) {
        return ResponseEntity.ok(userService.updateSkills(id, skillsDTO));
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updatePassword(@PathVariable Long id, @RequestBody @Valid UserPasswordDTO dto) {
        return ResponseEntity.ok(userService.updatePassword(id, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userAssembler.toModel(userService.updateUserFields(id, dto)));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<EntityModel<UserDTO>> logicDown(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deactivate(id));
    }

    @PutMapping("/reactivate/{id}")
    public ResponseEntity<EntityModel<UserDTO>> logicHigh(@PathVariable Long id) {
        return ResponseEntity.ok(userService.reactivate(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
