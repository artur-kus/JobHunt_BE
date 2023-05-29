package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8011")
@Slf4j
@RestController
@RequestMapping("/api/response")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    @GetMapping("/send")
    public ResponseEntity<?> sendRepliedByLoggedUser(@RequestParam Long jobId) {
        try {
            return new ResponseEntity<>(responseService.sendRepliedByLoggedUser(jobId), HttpStatus.OK);
        } catch (DefaultException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
