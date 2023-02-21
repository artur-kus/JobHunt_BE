package it.jobhunt.JobHunt.helper.security;

import it.jobhunt.JobHunt.enums.UserRole;
import it.jobhunt.JobHunt.helper.user.CreateUserHelper;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
    @NotNull(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Incorrect email. Please type valid email.")
    private String email;
    @NotNull(message = "Password is required")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
//            message = "Password is incorrect. Please type valid password")
    private String password;
    @NotNull(message = "User role is required.")
    private UserRole userRole;

    public SignupRequest(CreateUserHelper user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userRole = UserRole.USER;
    }
}
