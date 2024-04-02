package it.jobhunt.JobHunt.controler.dashboards;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.service.CompanyDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/dashboard/companies")
public class CompanyDashboardController {

    private final CompanyDashboardService service;

    @PostMapping("/findAllJobs")
    public ResponseEntity<?> findAllJobs(@RequestBody(required = false) JobFilter jobFilter) throws DefaultException {
        return new ResponseEntity<>(service.findAllJob(jobFilter), HttpStatus.OK);
    }
}