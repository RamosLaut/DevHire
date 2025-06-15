package TP_Final.devhire.Controllers;

import TP_Final.devhire.Model.DTOS.LikeDTO;
import TP_Final.devhire.Services.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @Operation(
            summary = "Dar like a una publicación",
            description = "Permite a un programador o empresa autenticado previamente dar like a una publicación.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "publicationId",
                            description = "ID de la publicación a la que se desea dar like",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "123")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Like realizado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado: el usuario no tiene permisos para realizar esta acción",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Publicación no encontrada",
                            content = @Content()
                    )
            }
    )
    @PostMapping("/publication/{publicationId}")
    public ResponseEntity<?> save(@PathVariable long publicationId) {
        likeService.save(publicationId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener todos los likes",
            description = "Permite obtener una colección con todos los likes disponibles.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Likes obtenidos exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "404", description = "No se encontraron likes disponibles")
    })
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<LikeDTO>>> findAll() {
        return ResponseEntity.ok(likeService.findAll());
    }

    @Operation(
            summary = "Obtener todos los likes propios",
            description = "Permite a usuarios con rol ROLE_DEV o ROLE_COMPANY obtener una colección con todos sus likes propios.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Likes propios obtenidos exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "404", description = "No se encontraron likes propios para el usuario")
    })
    @GetMapping("/ownLikes")
    public ResponseEntity<CollectionModel<EntityModel<LikeDTO>>> findOwnLikes() {
        return ResponseEntity.ok(likeService.findOwnLikes());
    }

    @Operation(
            summary = "Buscar un like por ID",
            description = "Permite a usuarios con rol ROLE_DEV o ROLE_ADMIN buscar un like específico por su ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like encontrado exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "404", description = "No se encontró un like con el ID especificado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<LikeDTO>> findById(@PathVariable long id) {
        return ResponseEntity.ok(likeService.findById(id));
    }

    @Operation(
            summary = "Obtener likes de una publicación",
            description = "Permite a usuarios con rol ROLE_DEV o ROLE_PUBLICATION ver los likes asociados a una publicación específica por su ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Likes de la publicación obtenidos exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. El usuario no tiene permisos para acceder a este recurso"),
            @ApiResponse(responseCode = "404", description = "No se encontraron likes o la publicación no existe")
    })
    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<CollectionModel<EntityModel<LikeDTO>>> findByPublicationId
    (@Parameter(description = "ID de la publicación cuyos likes se desean obtener", required = true)
     @PathVariable long publicationId) {
        return ResponseEntity.ok(likeService.findByPublicationId(publicationId));
    }

    @Operation(
            summary = "Obtener cantidad de likes de una publicación",
            description = "Permite a un programador o empresa autenticado previamente obtener la cantidad de likes de una publicación por su ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "publicationId",
                            description = "ID de la publicación",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", format = "int64")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cantidad de likes obtenida correctamente",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(type = "string", example = "42")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado: el usuario no tiene permisos para esta acción",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/publication/{publicationId}/quantity")
    public ResponseEntity<String> likesQuantity(@PathVariable long publicationId) {
        return ResponseEntity.ok(likeService.findLikesQuantityByPublicationId(publicationId));
    }

    @Operation(
            summary = "Eliminar un like propio",
            description = "Permite a un programador o empresa autenticado previamente eliminar un like propio.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID del like a eliminar",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", format = "int64")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Like eliminado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado: el usuario no tiene permisos para esta acción"
                    )
            }

    )
    @DeleteMapping("unlike/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        likeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
