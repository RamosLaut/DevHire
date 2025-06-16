package TP_Final.devhire.Controllers;
import TP_Final.devhire.Model.DTOS.CommentDTO;
import TP_Final.devhire.Services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @Operation(
            summary = "Agregar un comentario a una publicación",
            description = "Permite a un usuario autenticado (ROLE_DEV o ROLE_COMPANY) comentar una publicación específica.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "publicationId",
                            description = "ID de la publicación a comentar",
                            required = true,
                            example = "42"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Contenido del comentario",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comentario creado correctamente"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. El usuario autenticado no tiene permitido comentar esta publicación."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró la publicación con el ID especificado."
                    )
            }
    )
    @PostMapping("/publication/{publicationId}")
    public ResponseEntity<?> save(@RequestBody @Valid CommentDTO commentDTO, @PathVariable Long publicationId){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(commentDTO, publicationId));
    }

    @Operation(summary = "Obtener todos los comentarios")
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<CommentDTO>>> findAll(){
        return ResponseEntity.ok(commentService.findAll());
    }
    @Operation(
            summary = "Obtener comentarios propios",
            description = "Permite a un usuario autenticado (ROLE_DEV o ROLE_COMPANY) ver todos los comentarios que ha realizado.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de comentarios del usuario autenticado",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. El usuario no tiene permisos para realizar esta acción."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron comentarios del usuario autenticado."
                    )
            }
    )
    @GetMapping("/ownComments")
    public ResponseEntity<CollectionModel<EntityModel<CommentDTO>>> findOwnComments(){
        return ResponseEntity.ok(commentService.findOwnComments());
    }
    @Operation(
            summary = "Obtener un comentario por ID",
            description = "Permite a un usuario autenticado (ROLE_DEV o ROLE_COMPANY) buscar un comentario específico por su ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "commentId",
                            description = "ID del comentario a buscar",
                            required = true,
                            example = "10"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentario encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. El usuario no tiene permisos para ver este comentario."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró ningún comentario con el ID especificado."
                    )
            }
    )
    @GetMapping("/{commentId}")
    public ResponseEntity<EntityModel<CommentDTO>> findById(@PathVariable Long commentId){
        return ResponseEntity.ok(commentService.findById(commentId));
    }
    @Operation(
            summary = "Obtener comentarios de una publicación",
            description = "Permite a un usuario autenticado (ROLE_DEV o ROLE_COMPANY) obtener todos los comentarios realizados sobre una publicación identificada por su ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "publicationId",
                            description = "ID de la publicación cuyos comentarios se desean obtener",
                            required = true,
                            example = "35"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de comentarios de la publicación",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. El usuario no tiene permiso para ver estos comentarios."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró la publicación con el ID especificado o no tiene comentarios."
                    )
            }
    )
    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<CollectionModel<EntityModel<CommentDTO>>> findByPublicationId(@PathVariable long publicationId){
        return ResponseEntity.ok(commentService.findByPublicationId(publicationId));
    }
    @Operation(
            summary = "Actualizar contenido de un comentario",
            description = "Permite a un usuario autenticado (ROLE_DEV o ROLE_COMPANY) actualizar el contenido de un comentario que le pertenece.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Comentario con el contenido actualizado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentario actualizado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado. El usuario no tiene permiso para modificar este comentario."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró el comentario que se desea actualizar."
                    )
            }
    )

    @PatchMapping("/update")
    public ResponseEntity<EntityModel<CommentDTO>> updateContent(@RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok().body(commentService.updateContent(commentDTO));
    }
    @Operation(
            summary = "Eliminar un comentario propio",
            description = "Permite a un programador o empresa autenticado previamente eliminar un comentario propio.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "commentId",
                            description = "ID del comentario a eliminar",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "123")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Comentario eliminado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado: el comentario no pertenece al usuario autenticado",
                            content = @Content()
                    )
            }
    )
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteById(@PathVariable Long commentId){
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}
