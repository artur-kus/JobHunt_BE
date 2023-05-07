package it.jobhunt.JobHunt.helper.company;

import it.jobhunt.JobHunt.helper.user.CreateUserHelper;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCompanyByAdminHelper extends CreateCompanyByUserHelper {
    @NotNull
    public CreateUserHelper user;
}