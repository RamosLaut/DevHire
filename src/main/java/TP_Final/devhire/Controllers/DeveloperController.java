package TP_Final.devhire.Controllers;

import TP_Final.devhire.Assemblers.DeveloperAssembler;
import TP_Final.devhire.Model.DTOS.AcademicInfoDTO;
import TP_Final.devhire.Model.DTOS.DeveloperDTO;
import TP_Final.devhire.Model.DTOS.JobExperienceDTO;
import TP_Final.devhire.Model.DTOS.SkillsDTO;
import TP_Final.devhire.Model.Entities.DeveloperEntity;
import TP_Final.devhire.Services.DeveloperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Developers", description = "Operaciones relacionadas con programadores registrados en la plataforma")
@RestController
@RequestMapping("/dev")
public class DeveloperController {
    private final DeveloperService developerService;
    private final DeveloperAssembler developerAssembler;

    @Autowired
    public DeveloperController(DeveloperService developerService, DeveloperAssembler developerAssembler) {
        this.developerService = developerService;
        this.developerAssembler = developerAssembler;
    }

    @Operation(
            summary = "Obtener programador por ID",
            parameters = {
                    @Parameter(name = "id", description = "ID del programador", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Programador encontrado", content = @Content(schema = @Schema(implementation = DeveloperDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Programador no encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> getDevById(@PathVariable Long id){
        return ResponseEntity.ok(developerService.findById(id));
    }

    @Operation(
            summary = "Listar todos los programadores",
            responses = @ApiResponse(responseCode = "200", description = "Lista de programadores", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DeveloperDTO.class))))
    )
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<DeveloperDTO>>> listAllDevs() {
        List<EntityModel<DeveloperDTO>> users = developerService.findAll();
       return ResponseEntity.ok(
                CollectionModel.of(users));
    }

    @Operation(
            summary = "Listar programadores paginados",
            parameters = {
                    @Parameter(name = "page", description = "Número de página", example = "0"),
                    @Parameter(name = "size", description = "Cantidad de elementos por página", example = "10")
            },
            responses = @ApiResponse(responseCode = "200", description = "Lista paginada", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DeveloperDTO.class))))
    )
    @GetMapping("/page")
    public ResponseEntity<CollectionModel<EntityModel<DeveloperDTO>>> listAllDevsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            PagedResourcesAssembler<DeveloperEntity> pagedResourcesAssembler) {

        Page<DeveloperEntity> developerPage = developerService.findAllPage(page, size);

        PagedModel<EntityModel<DeveloperDTO>> pagedModel =
                pagedResourcesAssembler.toModel(developerPage, developerAssembler);

        return ResponseEntity.ok(pagedModel);
    }

    @Operation(
            summary = "Actualizar información académica",
            parameters = @Parameter(name = "id", description = "ID del programador"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lista de experiencias académicas",
                    required = true,
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AcademicInfoDTO.class)))
            ),
            responses = @ApiResponse(responseCode = "200", description = "Información actualizada")
    )
    @PutMapping("/academicInfo/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> updateAcademicInfo(@PathVariable Long id, @RequestBody List<AcademicInfoDTO> academicInfoDTOS) {
        return ResponseEntity.ok(developerService.updateAcademicInfo(id, academicInfoDTOS));
    }


    @Operation(
            summary = "Actualizar experiencia laboral",
            parameters = @Parameter(name = "id", description = "ID del programador"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lista de experiencias laborales",
                    required = true,
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobExperienceDTO.class)))
            )
    )
    @PutMapping("/jobExperience/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> updateJobExperience(@PathVariable Long id, @RequestBody List<JobExperienceDTO> jobExperienceDTOS) {
        return ResponseEntity.ok(developerService.updateJobExperience(id, jobExperienceDTOS));
    }

    @Operation(
            summary = "Actualizar habilidades del programador",
            parameters = @Parameter(name = "id", description = "ID del programador"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto con las habilidades",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SkillsDTO.class))
            )
    )
    @PutMapping("/skills/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> updateSkills(@PathVariable Long id, @Valid @RequestBody SkillsDTO skillsDTO) {
        return ResponseEntity.ok(developerService.updateSkills(id, skillsDTO));
    }
//
//    @PutMapping("/password/{id}")
//    public ResponseEntity<EntityModel<DeveloperDTO>> updatePassword(@PathVariable Long id, @RequestBody @Valid DeveloperPasswordDTO dto) {
//        return ResponseEntity.ok(developerService.updatePassword(id, dto));
//    }

    @Operation(
            summary = "Actualizar campos del programador",
            parameters = @Parameter(name = "id", description = "ID del programador"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Campos a actualizar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DeveloperDTO.class))
            )
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> updateDev(@PathVariable Long id, @RequestBody DeveloperDTO dto) {
        return ResponseEntity.ok(developerService.updateUserFields(id, dto));
    }

    //Metodo para generar link en assembler
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDev(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Desactivar programador (baja lógica)",
            parameters = @Parameter(name = "id", description = "ID del programador")
    )
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> logicDown(@PathVariable Long id) {
        return ResponseEntity.ok(developerService.deactivate(id));
    }

    @Operation(
            summary = "Reactivar programador (alta lógica)",
            parameters = @Parameter(name = "id", description = "ID del programador")
    )
    @PutMapping("/reactivate/{id}")
    public ResponseEntity<EntityModel<DeveloperDTO>> logicHigh(@PathVariable Long id) {
        return ResponseEntity.ok(developerService.reactivate(id));
    }

    @Operation(
            summary = "Eliminar programador",
            parameters = @Parameter(name = "id", description = "ID del programador"),
            responses = @ApiResponse(responseCode = "204", description = "Eliminación exitosa")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDev(@PathVariable Long id) {
        developerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
