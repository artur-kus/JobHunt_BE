package it.jobhunt.JobHunt.persistance;

import it.jobhunt.JobHunt.entity.Company;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.NotFoundException;

import java.util.List;

public interface CompanyDao {

    Company getCompany(Long idCompany) throws DefaultException, NotFoundException;

    List<Company> getUserCompanies() throws DefaultException;

    List<Long> getUserCompaniesIds() throws DefaultException;
}
