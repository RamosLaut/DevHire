package TP_Final.devhire.Controllers;

import TP_Final.devhire.Assemblers.PublicationAssembler;
import TP_Final.devhire.DTOS.PublicationDTO;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/publication")
public class PublicationController {

    private final PublicationService publicationService;
    private final PublicationAssembler publicationAssembler;
    @Autowired
    public PublicationController(PublicationService publicationService, PublicationAssembler publicationAssembler) {
        this.publicationService = publicationService;
        this.publicationAssembler = publicationAssembler;
    }
    @PostMapping
    public ResponseEntity<EntityModel<PublicationDTO>> save(@RequestBody PublicationEntity publicationEntity) {
        publicationService.save(publicationEntity);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(publicationAssembler.toModel(publicationEntity));
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PublicationDTO>>> findAll() {
        List<EntityModel<PublicationDTO>> publicationsDTO = publicationService.findAll().stream()
                .map(publicationAssembler::toModel)
                .toList();
        return ResponseEntity.ok(CollectionModel.of(publicationsDTO));
    }
    @GetMapping("/{publicationId}")
    public ResponseEntity<EntityModel<PublicationDTO>> findById(@PathVariable Long publicationId){
        return ResponseEntity.ok(publicationAssembler.toModel(publicationService.findById(publicationId)));
    }
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<CollectionModel<EntityModel<PublicationDTO>>>findAllByUserId(@PathVariable Long userId){
//        List<EntityModel<PublicationDTO>> publicationsDTO = publicationService.findByuserId(userId).stream()
//                .map(publicationAssembler::toModel)
//                .toList();
//        return ResponseEntity.ok(CollectionModel.of(publicationsDTO));
//    }
    @PatchMapping
    public ResponseEntity<EntityModel<PublicationDTO>>updateContent(@RequestBody PublicationEntity publicationEntity){
        publicationService.updateContent(publicationEntity);
        return ResponseEntity.ok(publicationAssembler.toModel(publicationEntity));
    }
    @DeleteMapping("/{publicationId}")
    public ResponseEntity<?> deleteById(@PathVariable Long publicationId){
        publicationService.deleteById(publicationId);
        return ResponseEntity.noContent().build();
    }
//    @DeleteMapping("/user/{userId}")
//    public ResponseEntity<?> deleteAllByUserId(@PathVariable Long userId){
//        publicationService.deleteByuserId(userId);
//        return ResponseEntity.noContent().build();
//    }
}
