package TP_Final.devhire.Controllers;
import TP_Final.devhire.Model.DTOS.CompanyDTO;
import TP_Final.devhire.Services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Companies", description = "Operaciones relacionadas con empresas registradas")
@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(
            summary = "Listar todas las empresas",
            description = "Retorna una colección de todas las empresas registradas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado exitoso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CompanyDTO.class))))
            }
    )
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<CompanyDTO>>> findAll(){
        return ResponseEntity.ok(companyService.findAll());
    }

    @Operation(
            summary = "Buscar empresa por ID",
            description = "Obtiene una empresa específica mediante su ID.",
            parameters = @Parameter(name = "id", description = "ID de la empresa", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa encontrada", content = @Content(schema = @Schema(implementation = CompanyDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CompanyDTO>> findById(@PathVariable Long id){
        return ResponseEntity.ok(companyService.findById(id));
    }

    @Operation(
            summary = "Buscar empresas por ubicación",
            description = "Devuelve todas las empresas registradas en la ubicación especificada.",
            parameters = @Parameter(name = "location", description = "Ubicación (ciudad, provincia, etc.)", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado exitoso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CompanyDTO.class))))
            }
    )
    @GetMapping("/location/{location}")
    public ResponseEntity<CollectionModel<EntityModel<CompanyDTO>>>findByLocation(@PathVariable String location){
        return ResponseEntity.ok((companyService.findByLocation(location)));
    }

    @Operation(
            summary = "Actualizar los datos de la empresa autenticada",
            description = "Permite a una empresa autenticada actualizar su propia información.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CompanyDTO.class),
                            examples = @ExampleObject(value = "{\"name\":\"Mi empresa\", \"location\":\"Buenos Aires\"}")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Empresa actualizada exitosamente. Devuelve un EntityModel con la empresa actualizada.",
                            content = @Content(schema = @Schema(implementation = CompanyDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. Solo empresas autenticadas pueden realizar esta operación."
                    )
            }
    )
    @PatchMapping("/update")
    public ResponseEntity<EntityModel<CompanyDTO>> updateOwnCompany(@RequestBody CompanyDTO company){
        return ResponseEntity.ok(companyService.update(company));
    }

    @Operation(
            summary = "Eliminar cuenta propia de empresa",
            description = "Elimina la cuenta de la empresa actualmente autenticada.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cuenta eliminada exitosamente"),
                    @ApiResponse(responseCode = "403", description = "No autorizado")
            }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOwnCompany(){
        companyService.deleteOwnAccount();
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Eliminar empresa por nombre",
            description = "Elimina una empresa en base a su nombre.",
            parameters = @Parameter(name = "name", description = "Nombre de la empresa a eliminar", required = true),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Empresa eliminada"),
                    @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
            }
    )
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name){
            boolean deleted = companyService.deleteByName(name);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
    }
}

