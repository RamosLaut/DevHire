package TP_Final.devhire.Controllers;
import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.Services.CompanyService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

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
    @GetMapping
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
    public ResponseEntity<EntityModel<CompanyDTO>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    @Operation(
            summary = "Actualizar empresa",
            description = "Actualiza los datos de una empresa utilizando su ID contenido en el objeto.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto de la empresa con los campos actualizados",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CompanyDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa actualizada correctamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos o empresa no encontrada")
            }
    )
    @PatchMapping
    public ResponseEntity<EntityModel<CompanyDTO>> updateCompany(@RequestBody CompanyDTO company){
        return ResponseEntity.ok(companyService.updateById(company));
    }

    @Operation(
            summary = "Eliminar empresa",
            description = "Elimina una empresa según su ID.",
            parameters = @Parameter(name = "id", description = "ID de la empresa a eliminar", required = true),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Empresa eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}