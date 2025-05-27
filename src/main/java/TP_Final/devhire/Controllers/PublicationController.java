package TP_Final.devhire.Controllers;

import TP_Final.devhire.DTOS.PublicationDTO;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Services.PublicationService;
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
    @PostMapping
    public ResponseEntity<EntityModel<PublicationDTO>> save(@RequestBody PublicationEntity publicationEntity) {

        return ResponseEntity.ok(publicationService.save(publicationEntity));
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PublicationDTO>>> findAll() {
        return ResponseEntity.ok(publicationService.findAll());
    }
    @GetMapping("/{publicationId}")
    public ResponseEntity<EntityModel<PublicationDTO>> findById(@PathVariable Long publicationId){
        return ResponseEntity.ok(publicationService.findById(publicationId));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<CollectionModel<EntityModel<PublicationDTO>>>findAllByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(publicationService.findByuserId(userId));
    }
    @PatchMapping
    public ResponseEntity<EntityModel<PublicationDTO>>updateContent(@RequestBody PublicationEntity publicationEntity){
        return ResponseEntity.ok(publicationService.updateContent(publicationEntity));
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
