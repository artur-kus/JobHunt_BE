package it.jobhunt.JobHunt.helper.candidate;

import it.jobhunt.JobHunt.entity.Address;
import it.jobhunt.JobHunt.helper.user.CreateUserHelper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCandidateHelper {
    @NotNull(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    private String lastName;
    private Address address;
    @NotNull(message = "User data is required")
    private CreateUserHelper user;
}
