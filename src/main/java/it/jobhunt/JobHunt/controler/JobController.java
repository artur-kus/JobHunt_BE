package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.job.CreateJobHelper;
import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.helper.job.JobHelper;
import it.jobhunt.JobHunt.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8011")
@Slf4j
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestBody(required = false) JobFilter jobFilter) {
        try {
            return new ResponseEntity<>(jobService.findAll(jobFilter), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateJobHelper createJobHelper) {
        try {
            return new ResponseEntity<>(jobService.create(createJobHelper), HttpStatus.OK);
        } catch (DefaultException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam Long jobId) {
        try {
            return new ResponseEntity<>(jobService.get(jobId), HttpStatus.OK);
        } catch (DefaultException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody JobHelper jobHelper) {
        try {
            return new ResponseEntity<>(jobService.edit(jobHelper), HttpStatus.OK);
        } catch (DefaultException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long jobId) {
        try {
            jobService.delete(jobId);
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
