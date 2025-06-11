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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publication")
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
    @PostMapping
    public ResponseEntity<EntityModel<PublicationDTO>> save(@RequestBody PublicationEntity publicationEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.save(publicationEntity));
    }


    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PublicationDTO>>> findAll() {
        return ResponseEntity.ok(publicationService.findAll());
    }

//    @Operation(
//            summary = "Obtener todas las publicaciones propias",
//            description = "Permite a un programador o empresa autenticado ver todas sus publicaciones.",
//            security = @SecurityRequirement(name = "bearerAuth")
//    )
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Listado de publicaciones propias obtenido correctamente",
//                    content = @Content(
//                            mediaType = "application/json",
//                            array = @ArraySchema(schema = @Schema(oneOf = {CompanyPublicationDTO.class, DeveloperPublicationDTO.class}))
//                    )
//            ),
//            @ApiResponse(responseCode = "403", description = "No autorizado, se requiere autenticación")
//    })
    @GetMapping("/ownPublications")
    public ResponseEntity<CollectionModel<EntityModel<PublicationDTO>>> findOwnPublications(){
        return ResponseEntity.ok(publicationService.findOwnPublications());
    }

//    @Operation(
//            summary = "Obtener publicación por ID",
//            description = "Permite a un desarrollador o empresa autenticado ver una publicación específica por su ID.",
//            security = @SecurityRequirement(name = "bearerAuth")
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "Publicación encontrada exitosamente",
//            content = @Content(
//                    mediaType = "application/json",
//                    schema = @Schema(oneOf = {CompanyPublicationDTO.class, DeveloperPublicationDTO.class})
//            )
//    )
    @GetMapping("/{publicationId}")
    public ResponseEntity<EntityModel<PublicationDTO>> findById(@PathVariable Long publicationId){
        return ResponseEntity.ok(publicationService.findById(publicationId));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<CollectionModel<EntityModel<PublicationDTO>>>findAllByDevId(@PathVariable Long devId){
        return ResponseEntity.ok(publicationService.findByDevId(devId));
    }

//    @Operation(
//            summary = "Actualizar contenido de una publicación propia",
//            description = "Permite a un programador o empresa autenticado modificar el contenido de una publicación propia.",
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    description = "JSON con el id de la publicación y el nuevo contenido",
//                    required = true,
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(
//                                    example = "{\"id\": 123, \"content\": \"Nuevo contenido\"}",
//                                    implementation = PublicationEntity.class
//                            )
//                    )
//            ),
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Publicación actualizada correctamente",
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(oneOf = {CompanyPublicationDTO.class, DeveloperPublicationDTO.class})
//                            )
//                    ),
//                    @ApiResponse(responseCode = "403", description = "No autorizado, se requiere autenticación")
//            },
//            security = @SecurityRequirement(name = "bearerAuth")
//    )
    @PatchMapping
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
    @DeleteMapping("/{publicationId}")
    public ResponseEntity<?> deleteById(@PathVariable Long publicationId){
        publicationService.deleteById(publicationId);
        return ResponseEntity.noContent().build();
    }
}
