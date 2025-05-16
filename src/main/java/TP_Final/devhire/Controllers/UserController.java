package TP_Final.devhire.Controllers;

import TP_Final.devhire.Assemblers.UserAssembler;
import TP_Final.devhire.DTOS.UserDTO;
import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @PostMapping("/register")
//    public ResponseEntity<UserEntity> register (@RequestBody UserDTO dto ){
//
//    }
}
