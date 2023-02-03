package it.jobhunt.JobHunt.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Salary {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float minWage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float maxWage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float salary;
}
