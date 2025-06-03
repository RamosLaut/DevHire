package TP_Final.devhire.Controllers;

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
    public ResponseEntity<EntityModel<Object>> save(@RequestBody PublicationEntity publicationEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.save(publicationEntity));
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Object>>> findAll() {
        return ResponseEntity.ok(publicationService.findAll());
    }
    @GetMapping("/ownPublications")
    public ResponseEntity<CollectionModel<EntityModel<Object>>> findOwnPublications(){
        return ResponseEntity.ok(publicationService.findOwnPublications());
    }
    @GetMapping("/{publicationId}")
    public ResponseEntity<EntityModel<Object>> findById(@PathVariable Long publicationId){
        return ResponseEntity.ok(publicationService.findById(publicationId));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<CollectionModel<EntityModel<Object>>>findAllByDevId(@PathVariable Long devId){
        return ResponseEntity.ok(publicationService.findByDevId(devId));
    }
    @PatchMapping
    public ResponseEntity<EntityModel<Object>>updateContent(@RequestBody PublicationEntity publicationEntity){
        return ResponseEntity.ok(publicationService.updateContent(publicationEntity));
    }
    @DeleteMapping("/{publicationId}")
    public ResponseEntity<?> deleteById(@PathVariable Long publicationId){
        publicationService.deleteById(publicationId);
        return ResponseEntity.noContent().build();
    }
}
