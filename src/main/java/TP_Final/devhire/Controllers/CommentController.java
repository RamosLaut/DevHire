package TP_Final.devhire.Controllers;
import TP_Final.devhire.DTOS.CommentDTO;
import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody CommentEntity comment){
        commentService.save(comment);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CommentDTO>>> findAll(){
        return ResponseEntity.ok(commentService.findAll());
    }
    @GetMapping("/{commentId}")
    public ResponseEntity<EntityModel<CommentDTO>> findById(@PathVariable Long commentId){
        return ResponseEntity.ok(commentService.findById(commentId));
    }
    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<CollectionModel<EntityModel<CommentDTO>>> findByPublicationId(@PathVariable long publicationId){
        return ResponseEntity.ok(commentService.findByPublicationId(publicationId));
    }
    @PatchMapping
    public ResponseEntity<EntityModel<CommentDTO>> updateContent(@RequestBody CommentEntity comment){
        commentService.updateContent(comment);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commentService.updateContent(comment));
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteById(@PathVariable Long commentId){
        commentService.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }
}
