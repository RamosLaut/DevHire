package TP_Final.devhire.Controllers;

import TP_Final.devhire.DTOS.PublicationDTO;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Services.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publication")
@Tag(name = "Publications", description = "Operaciones relacionadas con publicaciones de desarrolladores y empresas")
public class PublicationController {
    private final PublicationService publicationService;
    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @Operation(
            summary = "Crear una publicación",
            description = "Permite que un desarrollador o una empresa cree una publicación."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publicación creada"),
            @ApiResponse(responseCode = "403", description = "No autorizado, se requiere autenticación")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/post")
    public ResponseEntity<EntityModel<PublicationDTO>> save(@RequestBody PublicationEntity publicationEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.save(publicationEntity));
    }


    @Operation(
            summary = "Listar todas las publicaciones",
            description = "Devuelve una lista con todas las publicaciones públicas registradas"
    )
    @ApiResponse(responseCode = "200", description = "Listado de publicaciones obtenido correctamente")
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<PublicationDTO>>> findAll() {
        return ResponseEntity.ok(publicationService.findAll());
    }

    @Operation(
            summary = "Listar publicaciones propias",
            description = "Devuelve las publicaciones creadas por el usuario autenticado"
    )
    @ApiResponse(responseCode = "200", description = "Publicaciones propias obtenidas correctamente")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/ownPublications")
    public ResponseEntity<CollectionModel<EntityModel<PublicationDTO>>> findOwnPublications(){
        return ResponseEntity.ok(publicationService.findOwnPublications());
    }

    @Operation(
            summary = "Buscar publicación por ID",
            description = "Permite obtener una publicación específica según su ID",
            parameters = {
                    @Parameter(name = "publicationId", description = "ID de la publicación", required = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicación encontrada"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada")
    })
    @GetMapping("/{publicationId}")
    public ResponseEntity<EntityModel<PublicationDTO>> findById(@PathVariable Long publicationId){
        return ResponseEntity.ok(publicationService.findById(publicationId));
    }

    @GetMapping("/dev/{devId}")
    public ResponseEntity<CollectionModel<EntityModel<PublicationDTO>>>findAllByDevId(@PathVariable Long devId){
        return ResponseEntity.ok(publicationService.findByDevId(devId));
    }

    @Operation(
            summary = "Actualizar contenido de una publicación",
            description = "Permite actualizar el contenido de una publicación propia. Solo se permite si el usuario autenticado es el autor.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Entidad con ID y nuevo contenido",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PublicationEntity.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicación actualizada correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/update")
    public ResponseEntity<EntityModel<PublicationDTO>>updateContent(@RequestBody PublicationEntity publicationEntity){
        return ResponseEntity.ok(publicationService.updateContent(publicationEntity));
    }

    @Operation(
            summary = "Eliminar publicación propia",
            description = "Permite a un programador o empresa autenticado previamente eliminar una publicación propia.",
            parameters = {
                    @Parameter(
                            name = "publicationId",
                            description = "ID de la publicación a eliminar",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Publicación eliminada correctamente"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "No autorizado para eliminar esta publicación",
                            content = @Content()
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/delete/{publicationId}")
    public ResponseEntity<?> deleteById(@PathVariable Long publicationId){
        publicationService.deleteById(publicationId);
        return ResponseEntity.noContent().build();
    }
}
