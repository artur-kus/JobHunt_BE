package it.jobhunt.JobHunt.controler.dashboards;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.service.CompanyDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/dashboard/companies")
public class CompanyDashboardController {

    @Autowired
    private CompanyDashboardService service;

    @PostMapping("/findAllJobs")
    public ResponseEntity<?> findAllJobs(@RequestBody(required = false) JobFilter jobFilter) throws DefaultException {
        return new ResponseEntity<>(service.findAllJob(jobFilter), HttpStatus.OK);
    }
}