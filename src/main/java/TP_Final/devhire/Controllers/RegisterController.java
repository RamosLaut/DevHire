package TP_Final.devhire.Controllers;

import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;
    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/dev")
    public ResponseEntity<EntityModel<DeveloperDTO>> devRegister(@RequestBody DeveloperRegisterDTO devRegisterDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(registerService.devRegister(devRegisterDTO));
    }
    @PostMapping("/company")
    public ResponseEntity<EntityModel<CompanyDTO>> companyRegister(@RequestBody CompanyRegisterDTO companyRegisterDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(registerService.companyRegister(companyRegisterDTO));
    }
}
