package it.jobhunt.JobHunt.helper.company;

import it.jobhunt.JobHunt.entity.Address;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCompanyHelper {
    private String name;
    private String description;
    @Size(min = 11, max = 11)
    private String nip;
    private Address address;
}
