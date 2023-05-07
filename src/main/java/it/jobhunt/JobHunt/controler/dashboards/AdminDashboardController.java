package it.jobhunt.JobHunt.controler.dashboards;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.company.CreateCompanyByAdminHelper;
import it.jobhunt.JobHunt.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/admin")
public class AdminDashboardController {

    @Autowired
    private CompanyService companyService;

    @PutMapping("/company/create")
    public ResponseEntity<?> create(@RequestBody CreateCompanyByAdminHelper helper) {
        try {
            return new ResponseEntity<>(companyService.createByAdmin(helper), HttpStatus.OK);
        } catch (DefaultException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
