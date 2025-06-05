package TP_Final.devhire.Controllers;

import TP_Final.devhire.DTOS.FollowRequestDTO;
import TP_Final.devhire.DTOS.FollowResponseDTO;
import TP_Final.devhire.Services.FollowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;
    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public ResponseEntity<EntityModel<FollowResponseDTO>> saveFollow(@RequestBody @Valid FollowRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(followService.saveFollow(dto));
    }

    @GetMapping("/{type}/{followerId}/{followedId}")
    public ResponseEntity<EntityModel<FollowResponseDTO>> findById(
            @PathVariable String type,
            @PathVariable Long followerId,
            @PathVariable Long followedId) {
        return ResponseEntity.ok(followService.findById(type, followerId, followedId));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<FollowResponseDTO>>> findAll() {
        return ResponseEntity.ok(followService.findAll());
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<EntityModel<FollowResponseDTO>> deactivateFollow(@RequestBody @Valid FollowRequestDTO dto) {
        return ResponseEntity.ok(followService.deactivate(dto));
    }

    @PatchMapping("/reactivate")
    public ResponseEntity<EntityModel<FollowResponseDTO>> reactivateFollow(@RequestBody @Valid FollowRequestDTO dto) {
        return ResponseEntity.ok(followService.reactivate(dto));
    }

    @GetMapping("/followers/{type}/{followedId}")
    public ResponseEntity<CollectionModel<EntityModel<FollowResponseDTO>>> getFollowers(
            @PathVariable String type,
            @PathVariable Long followedId) {
        return ResponseEntity.ok(followService.getFollowers(type, followedId));
    }

    @GetMapping("/followings/{type}/{followerId}")
    public ResponseEntity<CollectionModel<EntityModel<FollowResponseDTO>>> getFollowings(@PathVariable String type, @PathVariable Long followerId) {
        return ResponseEntity.ok(followService.getFollowings(type, followerId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody FollowRequestDTO dto) {
        return followService.deleteById(dto);
    }

}

