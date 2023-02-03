package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.company.CompanyFilter;
import it.jobhunt.JobHunt.helper.company.CompanyHelper;
import it.jobhunt.JobHunt.helper.company.CreateCompanyHelper;
import it.jobhunt.JobHunt.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/companies/")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateCompanyHelper createCompanyHelper) {
        try {
            return new ResponseEntity<>(companyService.create(createCompanyHelper), HttpStatus.OK);
        } catch (DefaultException ex) {
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestBody(required = false) CompanyFilter companyFilter) {
        try {
            return new ResponseEntity<>(companyService.findAll(companyFilter), HttpStatus.OK);
//        } catch (DefaultException ex) {
//            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody CompanyHelper companyHelper) {
        try {
            return new ResponseEntity<>(companyService.edit(companyHelper), HttpStatus.OK);
        } catch (DefaultException ex) {
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
