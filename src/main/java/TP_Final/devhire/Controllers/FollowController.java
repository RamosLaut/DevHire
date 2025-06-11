package TP_Final.devhire.Controllers;

import TP_Final.devhire.DTOS.FollowRequestDTO;
import TP_Final.devhire.DTOS.FollowResponseDTO;
import TP_Final.devhire.Services.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@Tag(name = "Follows", description = "Gestión de relaciones de seguimiento entre usuarios y empresas")
public class FollowController {

    private final FollowService followService;
    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @Operation(
            summary = "Crear un seguimiento",
            description = "Permite a un usuario o empresa seguir a otro usuario o empresa.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FollowRequestDTO.class,
                                    example = "{\"followerId\":1,\"followerType\":\"DEVELOPER\",\"followedId\":2,\"followedType\":\"COMPANY\"}")
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Seguimiento creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping
    public ResponseEntity<EntityModel<FollowResponseDTO>> saveFollow(@RequestBody @Valid FollowRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(followService.saveFollow(dto));
    }

    @Operation(
            summary = "Buscar seguimiento por tipo e IDs",
            description = "Recupera un seguimiento existente por su tipo y los IDs de seguidor y seguido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Seguimiento encontrado"),
                    @ApiResponse(responseCode = "404", description = "Seguimiento no encontrado")
            }
    )
    @GetMapping("/{typeOfFollow}/{followerId}/{followedId}")
    public ResponseEntity<EntityModel<FollowResponseDTO>> findById(
            @PathVariable String typeOfFollow,
            @PathVariable Long followerId,
            @PathVariable Long followedId) {
        return ResponseEntity.ok(followService.findById(typeOfFollow, followerId, followedId));
    }

    @Operation(
            summary = "Listar todos los seguimientos",
            description = "Devuelve todos los seguimientos registrados en la plataforma."
    )
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<FollowResponseDTO>>> findAll() {
        return ResponseEntity.ok(followService.findAll());
    }

    @Operation(
            summary = "Desactivar un seguimiento",
            description = "Desactiva un seguimiento sin eliminarlo permanentemente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FollowRequestDTO.class)
                    )
            )
    )
    @PatchMapping("/deactivate")
    public ResponseEntity<EntityModel<FollowResponseDTO>> deactivateFollow(@RequestBody @Valid FollowRequestDTO dto) {
        return ResponseEntity.ok(followService.deactivate(dto));
    }

    @Operation(
            summary = "Reactivar un seguimiento",
            description = "Permite reactivar un seguimiento previamente desactivado.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FollowRequestDTO.class)
                    )
            )
    )

    @PatchMapping("/reactivate")
    public ResponseEntity<EntityModel<FollowResponseDTO>> reactivateFollow(@RequestBody @Valid FollowRequestDTO dto) {
        return ResponseEntity.ok(followService.reactivate(dto));
    }

    @Operation(
            summary = "Listar seguidores de una entidad",
            description = "Devuelve todos los seguidores de un usuario o empresa dado su tipo e ID."
    )
    @GetMapping("/followers/{followedType}/{followedId}")
    public ResponseEntity<CollectionModel<EntityModel<FollowResponseDTO>>> getFollowers(
            @PathVariable String followedType,
            @PathVariable Long followedId) {
        return ResponseEntity.ok(followService.getFollowers(followedType, followedId));
    }

    @Operation(
            summary = "Obtener seguidos",
            description = "Devuelve todas las entidades que sigue el usuario o empresa indicado."
    )
    @GetMapping("/followings/{followerType}/{followerId}")
    public ResponseEntity<CollectionModel<EntityModel<FollowResponseDTO>>> getFollowings(@PathVariable String followerType, @PathVariable Long followerId) {
        return ResponseEntity.ok(followService.getFollowings(followerType, followerId));
    }

    @Operation(
            summary = "Obtener mis seguidores",
            description = "Devuelve todos los seguidores del usuario o empresa autenticado."
    )
    @GetMapping("/ownFollowers")
    public ResponseEntity<CollectionModel<EntityModel<FollowResponseDTO>>> getOwnFollowers() {
        return ResponseEntity.ok(followService.getOwnFollowers());
    }

    @Operation(
            summary = "Obtener a quién sigo",
            description = "Devuelve todas las entidades que el usuario o empresa autenticado sigue."
    )
    @GetMapping("/ownFollowings")
    public ResponseEntity<CollectionModel<EntityModel<FollowResponseDTO>>> getOwnFollowings() {
        return ResponseEntity.ok(followService.getOwnFollowings());
    }

    @Operation(
            summary = "Eliminar seguimiento",
            description = "Elimina un seguimiento de forma permanente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FollowRequestDTO.class)
                    )
            )
    )
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody @Valid FollowRequestDTO dto) {
        return followService.deleteById(dto);
    }

}

