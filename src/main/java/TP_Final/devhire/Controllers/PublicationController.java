package TP_Final.devhire.Controllers;

import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publication")
public class PublicationController {

    private PublicationService publicationService;

    @Autowired
    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @PostMapping
    public ResponseEntity<PublicationEntity> createPublication(@RequestBody PublicationEntity publicationEntity) {
        publicationService.save(publicationEntity);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(publicationEntity);
    }

    @GetMapping
    public ResponseEntity<List<PublicationEntity>> findAllPublications() {
        return ResponseEntity.ok(publicationService.findAll());
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<List<PublicationEntity>>findAllByUserId(@PathVariable Long userId){
//        return ResponseEntity.ok(publicationService.findByuserId(userId));
//    }
    @GetMapping("/{publicationId}")
    public ResponseEntity<PublicationEntity>findPublicationById(@PathVariable Long publicationId){
        return ResponseEntity.ok(publicationService.findById(publicationId));
    }
//    @PatchMapping
//    public ResponseEntity<PublicationEntity>updateContent(@RequestBody PublicationEntity publicationEntity){
//        publicationService.updateContent(publicationEntity);
//        return ResponseEntity.ok(publicationEntity);
//    }
    @DeleteMapping("/{publicationId}")
    public ResponseEntity<Void>deletePublicationById(@PathVariable Long publicationId){
        publicationService.deleteById(publicationId);
        return ResponseEntity.noContent().build();
    }
    }
