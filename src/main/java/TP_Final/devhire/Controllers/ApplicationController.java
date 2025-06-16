package TP_Final.devhire.Controllers;

import TP_Final.devhire.Model.DTOS.ApplicationDTO;
import TP_Final.devhire.Model.DTOS.DeveloperApplicantDTO;
import TP_Final.devhire.Services.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
public class ApplicationController {
    private final ApplicationService applicationService;
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(applicationService.findAll());
    }
    @Operation(
            summary = "Obtener aplicaciones propias",
            description = "Permite a un programador autenticado (ROLE_DEV) ver sus aplicaciones a empleos, en caso de que existan.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de aplicaciones del programador",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ApplicationDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. Solo usuarios con rol ROLE_DEV pueden acceder."
                    )
            }
    )
    @GetMapping("/ownApplications")
    public ResponseEntity<?> findOwnApplications(){
        return ResponseEntity.ok(applicationService.findOwnApplications());
    }
    @Operation(
            summary = "Buscar aplicación por ID",
            description = "Permite a un usuario autenticado con rol ROLE_DEV o ROLE_COMPANY buscar una aplicación a empleo por su ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "applicationId",
                            description = "ID de la aplicación a buscar",
                            required = true,
                            example = "42"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Aplicación encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApplicationDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró ninguna aplicación con ese ID"
                    )
            }
    )
    @GetMapping("/{applicationId}")
    public ResponseEntity<?> findById(@PathVariable Long applicationId){
        return ResponseEntity.ok(applicationService.findById(applicationId));
    }
    @Operation(
            summary = "Listar aplicantes a un empleo",
            description = "Permite a un usuario con rol ROLE_COMPANY ver los programadores que aplicaron a una oferta de empleo publicada por su empresa.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "jobId",
                            description = "ID del empleo publicado",
                            required = true,
                            example = "10"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de desarrolladores que aplicaron al empleo",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DeveloperApplicantDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. Solo usuarios con rol ROLE_COMPANY pueden acceder."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró ninguna oferta de empleo con ese ID o no pertenece a la empresa autenticada."
                    )
            }
    )
    @GetMapping("/applicantsByJob/{jobId}")
    public ResponseEntity<CollectionModel<EntityModel<DeveloperApplicantDTO>>> getApplicantsByJobId(@PathVariable Long jobId){
        return ResponseEntity.ok(applicationService.findApplicantsByJobId(jobId));
    }
    @Operation(
            summary = "Eliminar una aplicación propia",
            description = "Permite a un usuario con rol ROLE_DEV eliminar una de sus propias aplicaciones a empleos, identificada por su ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "applicationId",
                            description = "ID de la aplicación a eliminar",
                            required = true,
                            example = "7"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Aplicación eliminada correctamente. No se retorna contenido."
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. Solo el programador dueño de la aplicación puede eliminarla."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró ninguna aplicación con ese ID asociada al usuario autenticado."
                    )
            }
    )
    @DeleteMapping("/delete/{applicationId}")
    public ResponseEntity<?> deleteOwnApplication(@PathVariable Long applicationId){
        applicationService.deleteApplicationById(applicationId);
        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Ver aplicaciones aceptadas",
            description = "Permite a un usuario con rol ROLE_DEV ver sus aplicaciones a empleos que han sido aceptadas.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aplicaciones aceptadas obtenidas exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "404", description = "No se encontraron aplicaciones aceptadas para el usuario")
    })
    @GetMapping("/acceptedApplications")
    public ResponseEntity<CollectionModel<EntityModel<ApplicationDTO>>> findAcceptedApplications(){
        return ResponseEntity.ok(applicationService.findAcceptedApplications());
    }
    @Operation(
            summary = "Ver aplicaciones rechazadas",
            description = "Permite a un desarrollador (ROLE_DEV) ver sus aplicaciones a empleo rechazadas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aplicaciones rechazadas encontradas correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron aplicaciones rechazadas para este desarrollador"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado. Este endpoint está restringido al rol ROLE_DEV")
    })
    @GetMapping("/rejectedApplications")
    public ResponseEntity<CollectionModel<EntityModel<ApplicationDTO>>> findRejectedApplications(){
        return ResponseEntity.ok(applicationService.findRejectedApplications());
    }
    @Operation(
            summary = "Listar aplicantes que cumplen con todos los requisitos técnicos",
            description = "Permite a un usuario con rol ROLE_COMPANY obtener una lista de programadores que aplicaron a una oferta de empleo y cumplen con todos los requisitos técnicos (hard skills) definidos en dicha oferta.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "jobId",
                            description = "ID de la oferta de empleo",
                            required = true,
                            example = "15"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de desarrolladores que cumplen con todos los requisitos técnicos de la oferta",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DeveloperApplicantDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. Solo usuarios con rol ROLE_COMPANY pueden acceder."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró la oferta de empleo con el ID indicado o no pertenece a la empresa autenticada."
                    )
            }
    )
    @GetMapping("/applicantsWithAllHardRequirements/{jobId}")
    public ResponseEntity<CollectionModel<EntityModel<DeveloperApplicantDTO>>> getApplicantsWithAllHardRequirements(@PathVariable Long jobId){
        return ResponseEntity.ok(applicationService.findApplicantsWithAllHardRequirements(jobId));
    }
    @Operation(
            summary = "Listar aplicantes que cumplen con al menos un requisito técnico",
            description = "Permite a un usuario con rol ROLE_COMPANY obtener una lista de programadores que aplicaron a una oferta de empleo y cumplen con al menos uno de los requisitos técnicos (hard skills) definidos en dicha oferta.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "jobId",
                            description = "ID de la oferta de empleo",
                            required = true,
                            example = "15"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de desarrolladores que cumplen con al menos un requisito técnico de la oferta",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DeveloperApplicantDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. Solo usuarios con rol ROLE_COMPANY pueden acceder."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró la oferta de empleo con el ID indicado o no pertenece a la empresa autenticada."
                    )
            }
    )
    @GetMapping("applicantsWithAnyHardRequirement/{jobId}")
    public ResponseEntity<CollectionModel<EntityModel<DeveloperApplicantDTO>>> getApplicantsWithAnyHardRequirement(@PathVariable Long jobId){
        return ResponseEntity.ok(applicationService.findApplicantsWithAnyHardRequirements(jobId));
    }
    @Operation(
            summary = "Listar aplicantes que cumplen con un mínimo de requisitos técnicos",
            description = "Permite a un usuario con rol ROLE_COMPANY obtener una lista de programadores que aplicaron a una oferta de empleo y cumplen con al menos una cantidad mínima de hard skills especificada por parámetro.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "jobId",
                            description = "ID de la oferta de empleo",
                            required = true,
                            example = "20"
                    ),
                    @Parameter(
                            name = "min",
                            description = "Cantidad mínima de requisitos técnicos (hard skills) que debe cumplir el desarrollador",
                            required = true,
                            example = "3"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de desarrolladores que cumplen con el mínimo de requisitos técnicos especificado",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DeveloperApplicantDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. Solo usuarios con rol ROLE_COMPANY pueden acceder."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró la oferta de empleo con el ID indicado o no pertenece a la empresa autenticada."
                    )
            }
    )
    @GetMapping("applicantsWithMinHardRequirement/{jobId}")
    public ResponseEntity<CollectionModel<EntityModel<DeveloperApplicantDTO>>> getApplicantsWithMinHardRequirement(@PathVariable Long jobId, @RequestParam int min){
        return ResponseEntity.ok(applicationService.findApplicantsWithMinHardRequirements(jobId, min));
    }
    @Operation(
            summary = "Aceptar aplicación",
            description = "Permite a un usuario con rol ROLE_COMPANY aceptar una aplicación a un empleo publicado por él mismo.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aplicación aceptada exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para aceptar esta aplicación"),
            @ApiResponse(responseCode = "404", description = "No se encontró la aplicación con el ID especificado")
    })
    @PutMapping("/acceptApplication/{applicationId}")
    public ResponseEntity<?> acceptApplication(@PathVariable Long applicationId){
        applicationService.acceptApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Rechazar aplicación",
            description = "Permite a un usuario con rol ROLE_COMPANY rechazar una aplicación a un empleo publicado por él mismo.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aplicación rechazada exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para rechazar esta aplicación"),
            @ApiResponse(responseCode = "404", description = "No se encontró la aplicación con el ID especificado")
    })
    @PutMapping("/rejectApplication/{applicationId}")
    public ResponseEntity<?> rejectApplication(@PathVariable Long applicationId){
        applicationService.rejectApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
}
