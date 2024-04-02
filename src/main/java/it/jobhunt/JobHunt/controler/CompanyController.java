package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.NotFoundException;
import it.jobhunt.JobHunt.helper.company.CompanyFilter;
import it.jobhunt.JobHunt.helper.company.CompanyHelper;
import it.jobhunt.JobHunt.helper.company.CreateCompanyByUserHelper;
import it.jobhunt.JobHunt.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestBody(required = false) CompanyFilter companyFilter) {
        return new ResponseEntity<>(companyService.findAll(companyFilter), HttpStatus.OK);
    }

    @PutMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateCompanyByUserHelper createCompanyByUserHelper) throws DefaultException {
        return new ResponseEntity<>(companyService.create(createCompanyByUserHelper), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam Long companyId) throws NotFoundException {
        return new ResponseEntity<>(companyService.get(companyId), HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody CompanyHelper companyHelper) throws DefaultException {
        return new ResponseEntity<>(companyService.edit(companyHelper), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long companyId) throws DefaultException {
        companyService.delete(companyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
