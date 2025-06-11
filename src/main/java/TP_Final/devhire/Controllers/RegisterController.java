package TP_Final.devhire.Controllers;

import TP_Final.devhire.DTOS.*;
import TP_Final.devhire.Services.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Registro de programador",
            description = "Permite a un programador registrarse en la aplicación.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "JSON con los datos necesarios para el registro del programador",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = """
                {
                  "email": "usuario@example.com",
                  "password": "string",
                  "name": "Juan",
                  "lastName": "Pérez",
                  "dni": "12345678",
                  "location": "Ciudad"
                }
                """,
                                    implementation = DeveloperRegisterDTO.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Programador registrado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DeveloperDTO.class)
                            )
                    )
            }
    )
    @PostMapping("/dev")
    public ResponseEntity<EntityModel<DeveloperDTO>> devRegister(@RequestBody DeveloperRegisterDTO devRegisterDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(registerService.devRegister(devRegisterDTO));
    }

    @Operation(
            summary = "Registro de empresa",
            description = "Permite a una empresa registrarse en la aplicación.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "JSON con los datos necesarios para el registro de la empresa",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = """
                {
                  "email": "empresa@example.com",
                  "password": "string",
                  "name": "Nombre Empresa",
                  "location": "Ciudad",
                  "description": "Descripción breve de la empresa"
                }
                """,
                                    implementation = CompanyRegisterDTO.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Empresa registrada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CompanyDTO.class)
                            )
                    )
            }
    )
    @PostMapping("/company")
    public ResponseEntity<EntityModel<CompanyDTO>> companyRegister(@RequestBody CompanyRegisterDTO companyRegisterDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(registerService.companyRegister(companyRegisterDTO));
    }
}
