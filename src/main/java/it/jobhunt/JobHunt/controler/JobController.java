package it.jobhunt.JobHunt.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8011")
@Slf4j
@RestController
@RequestMapping("/api/job")
public class JobController {

//    @PostMapping("/")
//    public ResponseEntity<String> create(@RequestBody Job request) {
//        try {
//            return new ResponseEntity<>(authService.register(request), HttpStatus.OK);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            log.error("Error while register user - " + request.getEmail() + ", error message: " + ex.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
}
