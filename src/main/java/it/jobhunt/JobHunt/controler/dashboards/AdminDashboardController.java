package it.jobhunt.JobHunt.controler.dashboards;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.company.CreateCompanyByAdminHelper;
import it.jobhunt.JobHunt.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/admin")
public class AdminDashboardController {

    private final CompanyService companyService;

    @PutMapping("/company/create")
    public ResponseEntity<?> create(@RequestBody CreateCompanyByAdminHelper helper) throws DefaultException {
        return new ResponseEntity<>(companyService.createByAdmin(helper), HttpStatus.OK);
    }
}
