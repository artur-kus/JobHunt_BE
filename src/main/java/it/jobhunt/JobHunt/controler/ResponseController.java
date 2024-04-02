package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8011")
@Slf4j
@RestController
@RequestMapping("/api/response")
public class ResponseController {

    private final ResponseService responseService;

    @GetMapping("/send")
    public ResponseEntity<?> sendRepliedByLoggedUser(@RequestParam Long jobId) throws DefaultException {
        return new ResponseEntity<>(responseService.sendRepliedByLoggedUser(jobId), HttpStatus.OK);
    }
}
