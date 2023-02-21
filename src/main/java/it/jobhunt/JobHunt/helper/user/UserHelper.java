package it.jobhunt.JobHunt.helper.user;

import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserHelper {
    private Long id;
    private String email;
    private String password;
    private UserRole role;
    private Boolean enabled;

    public UserHelper(User userHelper) {
        this.id = userHelper.getId();
        this.email = userHelper.getEmail();
        this.password =userHelper.getPassword();
        this.role = userHelper.getRole();
        this.enabled = userHelper.getEnabled();
    }
}
