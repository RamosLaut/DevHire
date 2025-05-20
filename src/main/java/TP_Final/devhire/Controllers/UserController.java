package TP_Final.devhire.Controllers;

import TP_Final.devhire.Assemblers.UserAssembler;
import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Entities.AcademicInfo;
import TP_Final.devhire.Entities.JobExperience;
import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Enums.HardSkills;
import TP_Final.devhire.Enums.SoftSkills;
import TP_Final.devhire.Mappers.UserMapper;
import TP_Final.devhire.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserMapper userMapper;
    private final UserAssembler userAssembler;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, UserAssembler userAssembler) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userAssembler = userAssembler;
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<UserDTO>> register (@RequestBody @Valid UserRegisterDTO dto ){
      UserEntity entity = userMapper.convertToEntity(dto);
        UserEntity saved = userService.register(entity);
        return ResponseEntity.ok(userAssembler.toModel(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> getUserById(@PathVariable Long id){
        UserEntity user = userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(userAssembler.toModel(user));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> listAllUsers(){
        List<EntityModel<UserDTO>> users = userService.findAll().stream()
                .map(userAssembler::toModel)
                .toList();
        return ResponseEntity.ok(
                CollectionModel.of(users,
                        linkTo(methodOn(UserController.class).listAllUsers()).withSelfRel())
        );
    }

//    @GetMapping("/page/{page}/{size}")
//    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> listAllUsersPage( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size){
//        List<EntityModel<UserDTO>> users = userService.findAllPage(page, size).stream()
//                .map(userAssembler::toModel)
//                .toList();
//        return ResponseEntity.ok(
//                CollectionModel.of(users,
//                        linkTo(methodOn(UserController.class).listAllUsersPage(page, size)).withSelfRel())
//        );
//    }

    @PutMapping("/academicInfo/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateUserAcademicInfo(@PathVariable Long id, @RequestBody List<AcademicInfoDTO> academicInfoDTOS){
        List<AcademicInfo> academicInfo = academicInfoDTOS.stream()
                .map(userMapper::convertToAcademicInfo)
                .toList();

        UserEntity updated = userService.updateAcademicInfo(id, academicInfo);
//        UserDTO updatedDTO = userMapper.converToDto(updated);
        return ResponseEntity.ok(userAssembler.toModel(updated));

    }

    @PutMapping("/jobExperience/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateJobExperience(@PathVariable Long id, @RequestBody List<JobExperienceDTO> jobExperienceDTOS){
        List<JobExperience> jobExperiences = jobExperienceDTOS.stream()
                .map(userMapper::convertToJobExperience)
                .toList();

        UserEntity updated = userService.updateJobExperience(id, jobExperiences);
        return ResponseEntity.ok(userAssembler.toModel(updated));

    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateSkills (@PathVariable Long id, @RequestBody List<HardSkills> hardSkills, List<SoftSkills> softSkills){
        UserEntity updated = userService.updateSkills(id, softSkills, hardSkills);
        return ResponseEntity.ok(userAssembler.toModel(updated));
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updatePassword (@PathVariable Long id, @RequestBody UserCredentialsDTO userCredentialsDTO){
        UserEntity entity = userMapper.convertToEntity(userCredentialsDTO);
        UserEntity updated = userService.updatePassword(id, entity.getPassword());
        return ResponseEntity.ok(userAssembler.toModel(updated));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<EntityModel<UserDTO>> logicDown (@PathVariable Long id){
        UserEntity updated = userService.deactivate(id);
        return ResponseEntity.ok(userAssembler.toModel(updated));
    }

    @PutMapping("/reactivate/{id}")
    public ResponseEntity<EntityModel<UserDTO>> logicHigh (@PathVariable Long id){
        UserEntity updated = userService.reactivate(id);
        return ResponseEntity.ok(userAssembler.toModel(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
