package it.jobhunt.JobHunt.entity;

import it.jobhunt.JobHunt.enums.JobRole;
import it.jobhunt.JobHunt.enums.JobStatus;
import it.jobhunt.JobHunt.enums.JobType;
import it.jobhunt.JobHunt.enums.ProgrammingLanguages;
import it.jobhunt.JobHunt.helper.Salary;
import it.jobhunt.JobHunt.helper.job.CreateJobHelper;
import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.helper.job.JobHelper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "JOB")
@Data
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Embedded
    private Salary salary;
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private JobRole role;
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private JobType type;
    @Column(name = "LANGUAGES")
    @JoinColumn(name = "ID_LANGUAGES")
    @ElementCollection(targetClass = ProgrammingLanguages.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<ProgrammingLanguages> languages;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private JobStatus status;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_COMPANY")
    private Company company;

    public Job(JobFilter helper) {
        this.id = helper.getId();
        this.name = helper.getName();
        this.salary = helper.getSalary();
    }

    public Job(CreateJobHelper createJobHelper, Company company) {
        this.name = createJobHelper.getName();
        this.salary = createJobHelper.getSalary();
        this.role = createJobHelper.getRole();
        this.type = createJobHelper.getType();
        this.languages = createJobHelper.getLanguages();
        this.status = JobStatus.INACTIVE;
        company.getJobs().add(this);
        this.company = company;
    }

    public void fillUpFields(JobHelper jobHelper) {
        if (jobHelper.getName() != null) {
            this.name = jobHelper.getName();
        }
        if (jobHelper.getDescription() != null) {
            this.description = jobHelper.getDescription();
        }
        if (jobHelper.getSalary() != null) {
            this.salary = jobHelper.getSalary();
        }
        if (jobHelper.getRole() != null) {
            this.role = jobHelper.getRole();
        }
        if (jobHelper.getType() != null) {
            this.type = jobHelper.getType();
        }
        if (jobHelper.getStatus() != null) {
            this.status = jobHelper.getStatus();
        }
        if (jobHelper.getLanguages() != null) {
            this.languages = jobHelper.getLanguages();
        }
    }
}