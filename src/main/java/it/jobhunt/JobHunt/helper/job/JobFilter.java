package it.jobhunt.JobHunt.helper.job;

import it.jobhunt.JobHunt.enums.JobRole;
import it.jobhunt.JobHunt.enums.JobType;
import it.jobhunt.JobHunt.enums.ProgrammingLanguages;
import it.jobhunt.JobHunt.helper.Salary;
import it.jobhunt.JobHunt.util.PageHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JobFilter {
    private Long id;
    private String name;
    private Salary salary;
    private List<JobRole> role;
    private List<JobType> type;
    private List<ProgrammingLanguages> languages;
    private List<Long> idCompanies;
    private PageHelper page;

    public PageHelper getPage() {
        return (page != null) ? page : new PageHelper();
    }
}
