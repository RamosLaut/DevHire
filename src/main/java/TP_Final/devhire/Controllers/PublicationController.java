package TP_Final.devhire.Controllers;

import TP_Final.devhire.Model.DTOS.PublicationDTO;
import TP_Final.devhire.Services.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
            description = "Permite a usuarios con rol ROLE_DEV o ROLE_COMPANY crear una publicación.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicación creada exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para crear publicaciones.")
    })
    @PostMapping("/post")
    public ResponseEntity<EntityModel<PublicationDTO>> save(@RequestBody @Valid PublicationDTO publicationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.save(publicationDTO));
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
            summary = "Obtener publicaciones propias",
            description = "Devuelve todas las publicaciones creadas por el usuario autenticado con rol ROLE_DEV o ROLE_COMPANY.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicaciones recuperadas exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a este recurso."),
            @ApiResponse(responseCode = "404", description = "No se encontraron publicaciones para el usuario actual.")
    })
    @GetMapping("/ownPublications")
    public ResponseEntity<CollectionModel<EntityModel<PublicationDTO>>> findOwnPublications(){
        return ResponseEntity.ok(publicationService.findOwnPublications());
    }

    @Operation(
            summary = "Buscar publicación por ID",
            description = "Permite a usuarios con rol ROLE_DEV o ROLE_COMPANY buscar una publicación por su ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicación encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ninguna publicación con el ID suministrado."),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a esta publicación")
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
            description = "Permite a usuarios con rol ROLE_DEV o ROLE_COMPANY actualizar el contenido de una publicación propia.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenido actualizado exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para modificar esta publicación"),
            @ApiResponse(responseCode = "404", description = "La publicación no fue encontrada")
    })
    @PatchMapping("/update")
    public ResponseEntity<EntityModel<PublicationDTO>>updateContent(@RequestBody PublicationDTO publicationDTO){
        return ResponseEntity.ok(publicationService.updateContent(publicationDTO));
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró la publicación con el ID dado."
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
