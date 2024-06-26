package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.NotFoundException;
import it.jobhunt.JobHunt.helper.job.CreateJobHelper;
import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.helper.job.JobHelper;
import it.jobhunt.JobHunt.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8011")
@Slf4j
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @PostMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestBody(required = false) JobFilter jobFilter) {
        return new ResponseEntity<>(jobService.findAll(jobFilter), HttpStatus.OK);
    }

    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateJobHelper createJobHelper) throws DefaultException {
        return new ResponseEntity<>(jobService.create(createJobHelper), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam Long jobId) throws DefaultException, NotFoundException {
        return new ResponseEntity<>(jobService.get(jobId), HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody JobHelper jobHelper) throws DefaultException, NotFoundException {
        return new ResponseEntity<>(jobService.edit(jobHelper), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long jobId) throws DefaultException, NotFoundException {
        jobService.delete(jobId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
