package it.jobhunt.JobHunt.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RESPONSE")
@Data
@NoArgsConstructor
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "CV_FILE_PATCH")
    private String cvFilePath;
    @Column(name = "CANDIDATE_FIRST_NAME")
    private String firstName;
    @Column(name = "CANDIDATE_LAST_NAME")
    private String lastName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CANDIDATE", referencedColumnName = "ID", insertable = false, updatable = false)
    private Candidate candidate;

    public Response(String email, String uploadFilePath) {
        this.email = email;
        this.cvFilePath = uploadFilePath;
    }

    public Response(Candidate candidate) {
        this.email = candidate.getUser().getEmail();
        this.firstName = candidate.getFirstName();
        this.lastName = candidate.getLastName();
        this.candidate = candidate;
    }
}
