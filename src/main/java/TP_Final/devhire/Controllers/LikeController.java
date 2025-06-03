package TP_Final.devhire.Controllers;

import TP_Final.devhire.Services.LikeService;
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
    @PostMapping("/publication/{publicationId}")
    public ResponseEntity<?>save(@PathVariable long publicationId){
        likeService.save(publicationId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Object>>>findAll(){
        return ResponseEntity.ok(likeService.findAll());
    }
    @GetMapping("/ownLikes")
    public ResponseEntity<CollectionModel<EntityModel<Object>>>findOwnLikes(){
        return ResponseEntity.ok(likeService.findOwnLikes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Object>>findById(@PathVariable long id){
        return ResponseEntity.ok(likeService.findById(id));
    }
    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<CollectionModel<EntityModel<Object>>> findByPublicationId(@PathVariable long publicationId){
        return ResponseEntity.ok(likeService.findByPublicationId(publicationId));
    }
    @GetMapping("/publication/{publicationId}/likesQuantity")
    public ResponseEntity<String> likesQuantity(@PathVariable long publicationId){
        return ResponseEntity.ok(likeService.findLikesQuantityByPublicationId(publicationId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteById(@PathVariable long id) {
        likeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
