package TP_Final.devhire.Controllers;

import TP_Final.devhire.Model.DTOS.ApplicationDTO;
import TP_Final.devhire.Model.DTOS.JobDTO;

import TP_Final.devhire.Model.Mappers.Mappers.Entities.SkillModel;
import TP_Final.devhire.Services.ApplicationService;

import TP_Final.devhire.Services.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(
            summary = "Crear una oferta de empleo",
            description = "Permite a un usuario con rol ROLE_COMPANY crear una oferta de empleo.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Oferta de empleo creada exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para crear empleos")
    })
    @PostMapping("/post")
    public ResponseEntity<?> save
            (@io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la oferta de empleo a crear",
                    required = true)
             @RequestBody JobDTO jobDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.save(jobDTO));
    }
    @Operation(
            summary = "Obtener todas las ofertas de empleo",
            description = "Permite a usuarios con rol ROLE_DEV o ROLE_COMPANY consultar todas las ofertas de empleo disponibles.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de empleos obtenido exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "404", description = "No hay ofertas de empleo disponibles")
    })
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }
    @Operation(
            summary = "Buscar una oferta de empleo por ID",
            description = "Permite a usuarios con rol ROLE_DEV o ROLE_COMPANY obtener los detalles de una oferta de empleo específica por su ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oferta de empleo encontrada exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a esta oferta"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna oferta con el ID proporcionado")
    })
    @GetMapping("/{jobId}")
    public ResponseEntity<?>findById
            (@Parameter(description = "ID de la oferta de empleo", required = true)
             @PathVariable long jobId){
        return ResponseEntity.ok(jobService.findById(jobId));
    }
    @Operation(
            summary = "Obtener requisitos de una oferta de empleo",
            description = "Permite a usuarios con rol ROLE_DEV o ROLE_COMPANY ver los requisitos de una oferta de empleo específica.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisitos obtenidos exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a esta oferta"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna oferta con el ID proporcionado")
    })
    @GetMapping("/{jobId}/requirements")
    public ResponseEntity<List<String>>findJobOfferRequirements
            (@Parameter(description = "ID de la oferta de empleo", required = true)
              @PathVariable long jobId){
        return ResponseEntity.ok(jobService.findJobRequirements(jobId));
    }
    @Operation(
            summary = "Obtener ofertas de empleo propias",
            description = "Permite a un usuario con rol ROLE_COMPANY obtener todas las ofertas de empleo que ha publicado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de ofertas de empleo obtenido exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "404", description = "No se encontraron ofertas de empleo publicadas por el usuario")
    })
    @GetMapping("/ownOffers")
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> findOwnOffers(){
        return ResponseEntity.ok(jobService.findOwnOffers());
    }
    @Operation(
            summary = "Ver habilidades disponibles para una oferta de empleo",
            description = "Permite a un usuario con rol ROLE_COMPANY ver las habilidades disponibles para agregar como requerimiento a una oferta de empleo específica.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidades obtenidas exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "404", description = "No se encontró la oferta de empleo con el ID proporcionado")
    })
    @GetMapping("/{jobId}/findAvailableSkills")
    public ResponseEntity<CollectionModel<SkillModel>> findAvailableSkills
            (@Parameter(description = "ID de la oferta de empleo", required = true)
              @PathVariable long jobId){
        return ResponseEntity.ok(jobService.getSkills(jobId));
    }
    @Operation(
            summary = "Agregar un requerimiento de habilidad a una oferta de empleo",
            description = "Permite a un usuario con rol ROLE_COMPANY agregar una habilidad como requerimiento a una oferta de empleo específica.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requerimiento agregado exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para modificar esta oferta"),
            @ApiResponse(responseCode = "404", description = "No se encontró la oferta de empleo con el ID proporcionado")
    })
    @PostMapping("/{id}/addRequirement")
    public ResponseEntity<?> addRequirement(
            @Parameter(description = "ID de la oferta de empleo", required = true)
            @PathVariable long id,
            @Parameter(description = "Nombre de la habilidad a agregar", required = true)
            @RequestParam String skill
    ) {
        jobService.addRequirement(id, skill);
        return ResponseEntity.ok("Requirement added");
    }
    @Operation(
            summary = "Buscar ofertas de empleo por habilidad requerida",
            description = "Permite a usuarios con rol ROLE_DEV o ROLE_COMPANY buscar ofertas de empleo filtrando por una habilidad requerida.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ofertas de empleo encontradas"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "404", description = "No se encontraron ofertas de empleo con la habilidad especificada")
    })
    @GetMapping("/findBySkill/{skill}")
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> findBySkill
            (@Parameter(description = "Habilidad requerida para filtrar las ofertas de empleo", required = true)
             @PathVariable String skill){
        return ResponseEntity.ok(jobService.findBySkill(skill));
    }
    @Operation(
            summary = "Buscar ofertas de empleo por nombre de empresa",
            description = "Permite a usuarios con rol ROLE_DEV o ROLE_COMPANY buscar ofertas de empleo filtrando por el nombre de la empresa que las publicó.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ofertas de empleo encontradas"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "404", description = "No se encontraron ofertas de empleo para la empresa especificada")
    })
    @GetMapping("findByCompany/{companyName}")
    public ResponseEntity<CollectionModel<EntityModel<JobDTO>>> findByCompany
            (@Parameter(description = "Nombre de la empresa para filtrar las ofertas de empleo", required = true)
             @PathVariable String companyName){
        return ResponseEntity.ok(jobService.findByCompanyName(companyName));
    }
    @Operation(
            summary = "Actualizar una oferta de empleo",
            description = "Permite a un usuario con rol ROLE_COMPANY actualizar una oferta de empleo propia especificada por su ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oferta de empleo actualizada exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para modificar esta oferta"),
            @ApiResponse(responseCode = "404", description = "No se encontró la oferta de empleo con el ID proporcionado")
    })
    @PatchMapping("/update/{jobId}")
    public ResponseEntity<?> updateJobOffer
            (@Parameter(description = "ID de la oferta de empleo a actualizar", required = true)
             @PathVariable long jobId,
             @Parameter(description = "Datos actualizados de la oferta de empleo", required = true)
             @RequestBody JobDTO job){
        return ResponseEntity.ok(jobService.update(jobId, job));
    }
    @Operation(
            summary = "Eliminar una oferta de empleo",
            description = "Permite a un usuario con rol ROLE_COMPANY eliminar una oferta de empleo propia especificada por su ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Oferta de empleo eliminada exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para eliminar esta oferta"),
            @ApiResponse(responseCode = "404", description = "No se encontró la oferta de empleo con el ID proporcionado")
    })
    @DeleteMapping("/delete/{jobId}")
    public ResponseEntity<?> deleteJobOffer
            (@Parameter(description = "ID de la oferta de empleo a eliminar", required = true)
             @PathVariable long jobId){
        jobService.deleteById(jobId);
        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Aplicar a una oferta de empleo",
            description = "Permite a un usuario con rol ROLE_DEV aplicar a una oferta de empleo específica por su ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aplicación realizada exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para aplicar"),
            @ApiResponse(responseCode = "404", description = "No se encontró la oferta de empleo con el ID proporcionado")
    })
    @PostMapping("/{jobId}/apply")
    public ResponseEntity<EntityModel<ApplicationDTO>> apply
            (@Parameter(description = "ID de la oferta de empleo a la que se aplica", required = true)
             @PathVariable Long jobId){
        return ResponseEntity.ok(applicationService.apply(jobId));
    }


}
