package it.jobhunt.JobHunt.helper.signUp;

import it.jobhunt.JobHunt.entity.Address;
import it.jobhunt.JobHunt.helper.user.CreateUserHelper;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterCandidate extends CreateUserHelper {
    @NotNull(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    private String lastName;
    private Address address;
}
