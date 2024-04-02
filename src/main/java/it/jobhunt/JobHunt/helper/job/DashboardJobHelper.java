package it.jobhunt.JobHunt.helper.job;

import it.jobhunt.JobHunt.entity.Address;
import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.enums.JobRole;
import it.jobhunt.JobHunt.enums.JobType;
import it.jobhunt.JobHunt.enums.ProgrammingLanguages;
import it.jobhunt.JobHunt.helper.Salary;
import lombok.Data;

import java.util.List;

@Data
public class DashboardJobHelper {
    private Long id;
    private String companyName;
    private String companyAddress;
    private String jobName;
    private Salary salary;
    private JobRole role;
    private JobType type;
    private List<ProgrammingLanguages> languages;

    public DashboardJobHelper(Job job) {

        this.id = job.getId();
        this.companyName = job.getCompany().getName();
        this.jobName = job.getName();
        this.salary = job.getSalary();
        this.role = job.getRole();
        this.type = job.getType();
        this.languages = job.getLanguages();
        if (job.getCompany() != null && job.getCompany().getAddress() != null) {
            this.companyAddress = getSimplifiedAddress(job.getCompany().getAddress());
        }
    }

    private String getSimplifiedAddress(Address address) {
        return address.getCity() + " " + address.getCountryCode();
    }
}
