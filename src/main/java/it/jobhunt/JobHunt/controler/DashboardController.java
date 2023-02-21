package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PostMapping("/findAllJobs")
    public ResponseEntity<?> findAllJobs(@RequestBody(required = false) JobFilter jobFilter) {
        try {
            return new ResponseEntity<>(dashboardService.findAllJobs(jobFilter), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
