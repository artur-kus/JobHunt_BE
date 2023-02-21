package it.jobhunt.JobHunt.helper.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserHelper {
    @NotNull(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Incorrect email. Please type valid email.")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
}
