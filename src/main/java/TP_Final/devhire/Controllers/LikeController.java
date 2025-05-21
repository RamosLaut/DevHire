package TP_Final.devhire.Controllers;

import TP_Final.devhire.Assemblers.LikeAssembler;
import TP_Final.devhire.DTOS.LikeDTO;
import TP_Final.devhire.Entities.LikeEntity;
import TP_Final.devhire.Services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
    @PostMapping
    public ResponseEntity<?>save(@RequestBody LikeEntity like){
        return ResponseEntity.ok(likeService.save(like));
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<LikeDTO>>>findAll(){
        return ResponseEntity.ok(likeService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<LikeDTO>>findById(@PathVariable long id){
        return ResponseEntity.ok(likeService.findById(id));
    }
    @GetMapping("/{publicationId}")
    public ResponseEntity<CollectionModel<EntityModel<LikeDTO>>> findByPublicationId(@PathVariable long publicationId){
        return ResponseEntity.ok(likeService.findByPublicationId(publicationId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteById(@PathVariable long id) {
        likeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
