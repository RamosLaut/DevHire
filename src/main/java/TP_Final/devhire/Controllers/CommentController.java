package TP_Final.devhire.Controllers;
import TP_Final.devhire.DTOS.CompanyCommentDTO;
import TP_Final.devhire.DTOS.DevCommentDTO;
import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
            summary = "Comentar una publicación",
            description = "Permite a un programador o empresa autenticado previamente comentar una publicación.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "JSON con el contenido del comentario",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"content\": \"Muy buen post\"}",
                                    implementation = CommentEntity.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comentario creado exitosamente. Devuelve el comentario guardado (CompanyCommentDTO o DevCommentDTO)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(anyOf = {CompanyCommentDTO.class, DevCommentDTO.class})
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado",
                            content = @Content()
                    )
            }
    )
    @PostMapping("/publication/{publicationId}")
    public ResponseEntity<?> save(@RequestBody CommentEntity comment, @PathVariable Long publicationId){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(comment, publicationId));
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Object>>> findAll(){
        return ResponseEntity.ok(commentService.findAll());
    }
    @Operation(
            summary = "Obtener propios comentarios",
            description = "Permite a un programador o empresa autenticado previamente ver sus propios comentarios.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentarios propios recuperados exitosamente. El cuerpo incluye una colección de comentarios (CompanyCommentDTO o DevCommentDTO).",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(anyOf = {CompanyCommentDTO.class, DevCommentDTO.class})
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/ownComments")
    public ResponseEntity<CollectionModel<EntityModel<Object>>> findOwnComments(){
        return ResponseEntity.ok(commentService.findOwnComments());
    }
    @Operation(
            summary = "Obtener un comentario por ID",
            description = "Permite a un programador o empresa autenticado previamente ver un comentario por su ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "commentId",
                            description = "ID del comentario a buscar",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentario recuperado exitosamente. El cuerpo incluye un DTO que puede ser CompanyCommentDTO o DevCommentDTO.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(anyOf = {CompanyCommentDTO.class, DevCommentDTO.class})
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/{commentId}")
    public ResponseEntity<EntityModel<Object>> findById(@PathVariable Long commentId){
        return ResponseEntity.ok(commentService.findById(commentId));
    }
    @Operation(
            summary = "Obtener comentarios por ID de publicación",
            description = "Permite a un programador o empresa autenticado previamente ver los comentarios de una publicación específica.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(
                            name = "publicationId",
                            description = "ID de la publicación de la que se desean obtener los comentarios",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentarios recuperados exitosamente. El cuerpo incluye una colección de DTOs que pueden ser CompanyCommentDTO o DevCommentDTO.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(anyOf = {CompanyCommentDTO.class, DevCommentDTO.class}))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado",
                            content = @Content()
                    )
            })
    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<CollectionModel<EntityModel<Object>>> findByPublicationId(@PathVariable long publicationId){
        return ResponseEntity.ok(commentService.findByPublicationId(publicationId));
    }
    @Operation(
            summary = "Actualizar contenido de un comentario",
            description = "Permite a un programador o empresa autenticado previamente actualizar el contenido de un comentario propio.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "JSON con el id del comentario y el nuevo contenido",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CommentEntity.class,
                                    example = "{\"id\": 123, \"content\": \"Nuevo contenido actualizado\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentario actualizado exitosamente. El cuerpo incluye el DTO actualizado (CompanyCommentDTO o DevCommentDTO).",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(anyOf = {CompanyCommentDTO.class, DevCommentDTO.class})
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado o el comentario no pertenece al usuario autenticado",
                            content = @Content()
                    )
            }
    )
    @PatchMapping("/update")
    public ResponseEntity<EntityModel<Object>> updateContent(@RequestBody CommentEntity comment){
        return ResponseEntity.ok().body(commentService.updateContent(comment));
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
