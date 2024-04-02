package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/home/")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @PostMapping("/findAllJobs")
    public ResponseEntity<?> findAllJobs(@RequestBody(required = false) JobFilter jobFilter) {
        return new ResponseEntity<>(homeService.findAllJobs(jobFilter), HttpStatus.OK);
    }

    @GetMapping("/job/get")
    public ResponseEntity<?> getJob(@RequestParam Long id) throws DefaultException {
        return new ResponseEntity<>(homeService.getJob(id), HttpStatus.OK);
    }
}
