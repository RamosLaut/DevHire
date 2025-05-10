package TP_Final.devhire.Controllers;

import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publication")
public class PublicationController {
    @Autowired
    private PublicationService publicationService;

    @GetMapping
    public ResponseEntity<List<PublicationEntity>>findAll(){
        return ResponseEntity.ok(publicationService.findAll());
    }
//    @GetMapping("/userId")
//    public ResponseEntity<List<PublicationEntity>>findAllByUserId(){
//        return
//    }
}
