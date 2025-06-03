package TP_Final.devhire.Controllers;

import TP_Final.devhire.Services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;
    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/dev/{devId}")
    public ResponseEntity<EntityModel<Object>> followDev(@PathVariable Long devId){
        return ResponseEntity.status(HttpStatus.CREATED).body(followService.followDev(devId));
    }

    @PostMapping("/company/{companyId}")
    public ResponseEntity<EntityModel<Object>> followCompany(@PathVariable Long companyId){
        return ResponseEntity.status(HttpStatus.CREATED).body(followService.followCompany(companyId));
    }
}
