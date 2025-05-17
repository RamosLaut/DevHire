package TP_Final.devhire.Controllers;

import TP_Final.devhire.Assemblers.UserAssembler;
import TP_Final.devhire.DTOS.UserDTO;
import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Mappers.UserMapper;
import TP_Final.devhire.Services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
    public ResponseEntity<EntityModel<UserDTO>> register (@RequestBody @Valid UserDTO dto ){
      UserEntity entity = userMapper.converToEntity(dto);
        UserEntity saved = userService.register(entity);

        return ResponseEntity
            .created(linkTo(methodOn(UserController.class).getUserById(saved.getUser_id())).toUri())
                .body(userAssembler.toModel(saved));
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
}
