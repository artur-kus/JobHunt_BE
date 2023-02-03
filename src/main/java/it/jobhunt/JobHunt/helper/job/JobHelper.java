package it.jobhunt.JobHunt.helper.job;

import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.enums.JobRole;
import it.jobhunt.JobHunt.enums.JobStatus;
import it.jobhunt.JobHunt.enums.JobType;
import it.jobhunt.JobHunt.enums.ProgrammingLanguages;
import it.jobhunt.JobHunt.helper.Salary;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JobHelper {
    private Long id;
    private String name;
    private String description;
    private Salary salary;
    private JobRole role;
    private JobType type;
    private List<ProgrammingLanguages> languages;
    private JobStatus status;
    private Long idCompany;

    public JobHelper(Job job) {
        this.id = job.getId();
        this.name = job.getName();
        this.description = job.getDescription();
        this.salary = job.getSalary();
        this.role = job.getRole();
        this.type = job.getType();
        this.languages = job.getLanguages();
        this.status = job.getStatus();
        this.idCompany = job.getCompany().getId();
    }
}
