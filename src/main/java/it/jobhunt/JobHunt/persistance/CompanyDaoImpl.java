package it.jobhunt.JobHunt.persistance;

import it.jobhunt.JobHunt.entity.Company;
import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.enums.UserRole;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.repository.CompanyRepository;
import it.jobhunt.JobHunt.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDaoImpl implements CompanyDao {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company getCompany(Long idCompany) throws DefaultException {
        User user = JwtUtils.getLoggedUser();
        UserRole role = User.getRole(user);
        return ((role.equals(UserRole.COMPANY))
                ? companyRepository.findByIdAndUserEmail(idCompany, user.getEmail())
                : companyRepository.findById(idCompany))
                .orElseThrow(() -> new DefaultException("Company with id " + idCompany + " has not exist."));
    }

    @Override
    public List<Company> getUserCompanies() throws DefaultException {
        User user = JwtUtils.getLoggedUser();
        if (user.getRole() != null && user.getRole().equals(UserRole.COMPANY) && user.getId() != null) {
            return companyRepository.findAllByUserEmail(user.getEmail());
        } else {
            String email = user.getEmail() != null ? user.getEmail() : null;
            throw new DefaultException("User " + email + " are not company.");
        }
    }

    @Override
    public List<Long> getUserCompaniesIds() throws DefaultException {
        User user = JwtUtils.getLoggedUser();
        if (user.getRole() != null && user.getRole().equals(UserRole.COMPANY) && user.getId() != null) {
            return companyRepository.findAllIdsByUserEmail(user.getEmail());
        } else {
            String email = user.getEmail() != null ? user.getEmail() : null;
            throw new DefaultException("User " + email + " are not company.");
        }
    }
}
