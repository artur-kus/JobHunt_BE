package it.jobhunt.JobHunt.helper.company;

import it.jobhunt.JobHunt.entity.Address;
import it.jobhunt.JobHunt.util.PageHelper;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyFilter {
    private Long id;
    private String name;
    private String description;
    private String nip;
    private Address address;
    private PageHelper page;

    public PageHelper getPage() {
        return (page != null) ? page : new PageHelper();
    }
}
