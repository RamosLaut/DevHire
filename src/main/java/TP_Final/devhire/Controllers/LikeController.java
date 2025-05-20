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
    private final LikeAssembler assembler;

    @Autowired
    public LikeController(LikeService likeService, LikeAssembler assembler) {
        this.likeService = likeService;
        this.assembler = assembler;
    }
    @PostMapping
    public ResponseEntity<?>save(@RequestBody LikeEntity like){
        likeService.save(like);
        return ResponseEntity.ok(assembler.toModel(like));
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<LikeDTO>>>findAll(){
        List<EntityModel<LikeDTO>> likes = likeService.findAll().stream()
                .map(assembler::toModel)
                .toList();
        return ResponseEntity.ok(CollectionModel.of(likes));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<LikeDTO>>findById(@PathVariable long id){
        return ResponseEntity.ok(assembler.toModel(likeService.findById(id)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteById(@PathVariable long id) {
        likeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
