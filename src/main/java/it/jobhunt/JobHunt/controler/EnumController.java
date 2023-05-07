package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.enums.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8011")
@RestController
@RequestMapping("/api/enum")
public class EnumController {

    @GetMapping("/user-roles")
    public List<UserRole> userRolesEnum() {
        return Arrays.asList(UserRole.values());
    }

    @GetMapping("/programming-languages")
    public List<ProgrammingLanguages> programmingLanguagesEnum() {
        return Arrays.asList(ProgrammingLanguages.values());
    }

    @GetMapping("/job-roles")
    public List<JobRole> jobRolesEnum() {
        return Arrays.asList(JobRole.values());
    }

    @GetMapping("/job-types")
    public List<JobType> jobTypesEnum() {
        return Arrays.asList(JobType.values());
    }


    @GetMapping("/countries")
    public List<CountryCode> countriesEnum() {
        return Arrays.asList(CountryCode.values());
    }
}
