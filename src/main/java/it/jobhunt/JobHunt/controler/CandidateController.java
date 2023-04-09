package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.candidate.CandidateFilter;
import it.jobhunt.JobHunt.helper.candidate.CandidateHelper;
import it.jobhunt.JobHunt.helper.candidate.CreateCandidateHelper;
import it.jobhunt.JobHunt.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestBody(required = false) CandidateFilter candidateFilter) {
        try {
            return new ResponseEntity<>(candidateService.findAll(candidateFilter), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateCandidateHelper helper) {
        try {
            return new ResponseEntity<>(candidateService.create(helper), HttpStatus.OK);
        } catch (DefaultException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam Long candidateId) {
        try {
            return new ResponseEntity<>(candidateService.get(candidateId), HttpStatus.OK);
        } catch (DefaultException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody CandidateHelper candidateHelper) {
        try {
            return new ResponseEntity<>(candidateService.edit(candidateHelper), HttpStatus.OK);
        } catch (DefaultException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long candidateId) {
        try {
            candidateService.delete(candidateId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DefaultException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
