package it.jobhunt.JobHunt.entity;

import it.jobhunt.JobHunt.helper.candidate.CandidateFilter;
import it.jobhunt.JobHunt.helper.candidate.CandidateHelper;
import it.jobhunt.JobHunt.helper.candidate.CreateCandidateHelper;
import it.jobhunt.JobHunt.helper.signUp.RegisterCandidate;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CANDIDATE")
@Data
@NoArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Embedded
    private Address address;
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    public Candidate(CreateCandidateHelper helper) {
        this.firstName = helper.getFirstName();
        this.lastName = helper.getLastName();
        this.address = helper.getAddress();
    }

    public Candidate(CandidateFilter candidateFilter) {
        this.firstName = candidateFilter.getFirstName();
        this.lastName = candidateFilter.getLastName();
        this.address = candidateFilter.getAddress();
        if (candidateFilter.getUser() != null) {
            this.user = new User(candidateFilter.getUser());
        }
    }

    public Candidate(RegisterCandidate request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.address = request.getAddress();
    }

    public void fillUpFields(CandidateHelper candidateHelper) {
        this.firstName = candidateHelper.getFirstName();
        this.lastName = candidateHelper.getLastName();
        this.address = candidateHelper.getAddress();
    }
}
