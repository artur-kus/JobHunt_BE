package it.jobhunt.JobHunt.helper.candidate;

import it.jobhunt.JobHunt.entity.Address;
import it.jobhunt.JobHunt.helper.user.UserHelper;
import it.jobhunt.JobHunt.util.PageHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CandidateFilter {
    private Long id;
    private String firstName;
    private String lastName;
    private Address address;
    private UserHelper user;
    private PageHelper page;

    public PageHelper getPage() {
        return (page != null) ? page : new PageHelper();
    }
}
