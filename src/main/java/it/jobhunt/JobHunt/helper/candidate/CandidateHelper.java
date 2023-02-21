package it.jobhunt.JobHunt.helper.candidate;

import it.jobhunt.JobHunt.entity.Address;
import it.jobhunt.JobHunt.entity.Candidate;
import it.jobhunt.JobHunt.helper.user.UserHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CandidateHelper {
    private Long id;
    private String firstName;
    private String lastName;
    private Address address;
    private UserHelper user;

    public CandidateHelper(Candidate candidate) {
        this.id = candidate.getId();
        this.firstName = candidate.getFirstName();
        this.lastName = candidate.getLastName();
        this.address = candidate.getAddress();
        this.user = new UserHelper(candidate.getUser());
    }
}