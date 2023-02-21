package it.jobhunt.JobHunt.entity;

import it.jobhunt.JobHunt.helper.company.CompanyFilter;
import it.jobhunt.JobHunt.helper.company.CompanyHelper;
import it.jobhunt.JobHunt.helper.company.CreateCompanyHelper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "COMPANY")
@Data
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "NIP")
    private String nip;
    @Embedded
    private Address address;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Job> jobs;

    public Company(Long id) {
        this.id = id;
    }

    public Company(CreateCompanyHelper createCompanyHelper, User user) {
        this.name = createCompanyHelper.getName();
        this.description = createCompanyHelper.getDescription();
        this.nip = createCompanyHelper.getNip();
        this.address = createCompanyHelper.getAddress();
        this.users = Collections.singletonList(user);
    }

    public Company(CompanyFilter companyHelper) {
        this.name = companyHelper.getName();
        this.description = companyHelper.getDescription();
        this.nip = companyHelper.getNip();
        this.address = companyHelper.getAddress();
    }

    public void fillUpFields(CompanyHelper companyHelper) {
        this.name = companyHelper.getName();
        this.description = companyHelper.getDescription();
        this.address = companyHelper.getAddress();
    }
}
