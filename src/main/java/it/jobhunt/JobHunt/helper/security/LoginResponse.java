package it.jobhunt.JobHunt.helper.security;

import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String email;
    private String token;
    private UserRole role;

    public LoginResponse(User user, String token) {
        this.email = user.getEmail();
        this.token = token;
        this.role = user.getRole();
    }
}
