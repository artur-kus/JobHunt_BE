package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.enums.CountryCode;
import it.jobhunt.JobHunt.enums.JobRole;
import it.jobhunt.JobHunt.enums.ProgrammingLanguages;
import it.jobhunt.JobHunt.enums.UserRole;
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

    @GetMapping("/programming-languages")
    public List<ProgrammingLanguages> programmingLanguagesEnum() {
        return Arrays.asList(ProgrammingLanguages.values());
    }

    @GetMapping("/job-roles")
    public List<JobRole> jobRolesEnum() {
        return Arrays.asList(JobRole.values());
    }

    @GetMapping("/user-roles")
    public List<UserRole> userRoleEnum() {
        return Arrays.asList(UserRole.values());
    }

    @GetMapping("/countries")
    public List<CountryCode> countriesEnum() {
        return Arrays.asList(CountryCode.values());
    }
}
