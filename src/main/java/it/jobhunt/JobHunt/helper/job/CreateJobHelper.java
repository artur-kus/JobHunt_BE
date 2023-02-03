package it.jobhunt.JobHunt.helper.job;

import it.jobhunt.JobHunt.enums.JobRole;
import it.jobhunt.JobHunt.enums.JobType;
import it.jobhunt.JobHunt.enums.ProgrammingLanguages;
import it.jobhunt.JobHunt.helper.Salary;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateJobHelper {
    private String name;
    private String description;
    private Salary salary;
    private JobRole role;
    private JobType type;
    private List<ProgrammingLanguages> languages;
    @NotNull
    private Long idCompany;
}
