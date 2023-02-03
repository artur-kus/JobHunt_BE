package it.jobhunt.JobHunt.helper.company;

import it.jobhunt.JobHunt.entity.Address;
import it.jobhunt.JobHunt.entity.Company;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyHelper {
    private Long id;
    private String name;
    private String description;
    private String nip;
    private Address address;

    public CompanyHelper(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
        this.nip = company.getNip();
        this.address = company.getAddress();
    }
}
