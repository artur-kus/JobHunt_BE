package it.jobhunt.JobHunt.service;

import it.jobhunt.JobHunt.entity.Company;
import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.enums.UserRole;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.NotFoundException;
import it.jobhunt.JobHunt.helper.company.CompanyFilter;
import it.jobhunt.JobHunt.helper.company.CompanyHelper;
import it.jobhunt.JobHunt.helper.company.CreateCompanyByAdminHelper;
import it.jobhunt.JobHunt.helper.company.CreateCompanyByUserHelper;
import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.persistance.CompanyDao;
import it.jobhunt.JobHunt.persistance.UserDao;
import it.jobhunt.JobHunt.repository.CompanyRepository;
import it.jobhunt.JobHunt.repository.JobRepository;
import it.jobhunt.JobHunt.repository.UserRepository;
import it.jobhunt.JobHunt.repository.specification.JobSpecification;
import it.jobhunt.JobHunt.util.JwtUtils;
import it.jobhunt.JobHunt.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService implements CrudOperation<Company, CompanyHelper, CreateCompanyByUserHelper, CompanyFilter> {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Page<CompanyHelper> findAll(CompanyFilter companyFilter) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIgnoreCase();
        if (companyFilter == null) {
            companyFilter = new CompanyFilter();
        }
        Company company = new Company(companyFilter);
        Example<Company> companyExample = Example.of(company, exampleMatcher);
        Page<Company> page = companyRepository.findAll(companyExample, PageHelper.getPage(companyFilter.getPage()));
        List<CompanyHelper> companyHelpers = page.getContent().stream().map(CompanyHelper::new).toList();
        return new PageImpl<>(companyHelpers, page.getPageable(), companyHelpers.size());
    }

    @Override
    public CompanyHelper create(CreateCompanyByUserHelper createCompanyByUserHelper) throws DefaultException {
        User user = JwtUtils.getLoggedUser();
        if (user.getEmail() != null && !user.getRole().equals(UserRole.USER) && userRepository.existsByEmail(user.getEmail())) {
            Company company = new Company(createCompanyByUserHelper, user);
            company = companyRepository.save(company);
            return new CompanyHelper(company);
        } else throw new DefaultException("User doesn't exist");
    }

    public CompanyHelper createByAdmin(CreateCompanyByAdminHelper helper) throws DefaultException {
        User user = userDao.getOrCreate(helper.getUser().getEmail(), helper.getUser().getPassword(), UserRole.COMPANY);
        Company company = new Company(helper, user);
        company = companyRepository.save(company);
        return new CompanyHelper(company);
    }

    @Override
    public CompanyHelper get(Long companyId) throws NotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException(Company.class));
        return new CompanyHelper(company);
    }

    @Override
    public CompanyHelper edit(CompanyHelper companyHelper) throws DefaultException, NotFoundException {
        if (companyHelper.getId() == null) throw new DefaultException("Please choose company to edit");
        Company company = companyDao.getCompany(companyHelper.getId());
        company.fillUpFields(companyHelper);
        company = companyRepository.save(company);
        return new CompanyHelper(company);
    }

    @Override
    public void delete(Long id) throws DefaultException {
        //todo
    }

    public Page<Job> findAllJobs(JobFilter helper) throws DefaultException {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIgnoreCase();
        if (helper == null) {
            helper = new JobFilter();
        }
        User user = JwtUtils.getLoggedUser();
        if (user.getEmail() != null && user.getRole().equals(UserRole.COMPANY) && userRepository.existsByEmail(user.getEmail())) {
            List<Long> idCompanies = companyDao.getUserCompaniesIds();
            helper.setIdCompanies(idCompanies);
        }
        Job job = new Job(helper);
        Example<Job> companyExample = Example.of(job, exampleMatcher);
        return jobRepository.findAll(JobSpecification.get(companyExample, helper), PageHelper.getPage(helper.getPage()));
    }
}

