package TP_Final.devhire.Controllers;

import TP_Final.devhire.Assemblers.CommentAssembler;
import TP_Final.devhire.DTOS.CommentDTO;
import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentAssembler assembler;
    @Autowired
    public CommentController(CommentService commentService, CommentAssembler assembler) {
        this.commentService = commentService;
        this.assembler = assembler;
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody CommentEntity comment){
        commentService.save(comment);
        return ResponseEntity.ok(assembler.toModel(comment));
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CommentDTO>>> findAll(){
        List<EntityModel<CommentDTO>>comments = commentService.findAll().stream().map(assembler::toModel).toList();
        return ResponseEntity.ok(CollectionModel.of(comments));
    }
    @GetMapping("/{commentId}")
    public ResponseEntity<EntityModel<CommentDTO>> findById(@PathVariable Long commentId){
        return ResponseEntity.ok(assembler.toModel(commentService.findById(commentId)));
    }
    @GetMapping("/{publicationId}")
    public ResponseEntity<CollectionModel<EntityModel<CommentDTO>>> findByPublicationId(@PathVariable long publicationId){
        List<EntityModel<CommentDTO>> comments = commentService.findByPublicationId(publicationId).stream()
                .map(assembler::toModel)
                .toList();
        return ResponseEntity.ok(CollectionModel.of(comments));
    }
    @PatchMapping
    public ResponseEntity<EntityModel<CommentDTO>> updateContent(@RequestBody CommentEntity comment){
        commentService.updateContent(comment);
        return ResponseEntity.ok(assembler.toModel(comment));
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteById(@PathVariable Long commentId){
        commentService.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }

}
