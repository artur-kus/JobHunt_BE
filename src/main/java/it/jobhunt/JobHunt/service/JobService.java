package it.jobhunt.JobHunt.service;

import it.jobhunt.JobHunt.entity.Company;
import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.enums.JobStatus;
import it.jobhunt.JobHunt.enums.UserRole;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.job.CreateJobHelper;
import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.helper.job.JobHelper;
import it.jobhunt.JobHunt.persistance.JobDao;
import it.jobhunt.JobHunt.repository.CompanyRepository;
import it.jobhunt.JobHunt.repository.JobRepository;
import it.jobhunt.JobHunt.repository.specification.JobSpecification;
import it.jobhunt.JobHunt.util.JwtUtils;
import it.jobhunt.JobHunt.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService implements CrudOperation<Job, JobHelper, CreateJobHelper, JobFilter> {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobDao jobDao;


    @Override
    public Page<JobHelper> findAll(JobFilter helper) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIgnoreCase();
        if (helper == null) {
            helper = new JobFilter();
        }
        Job job = new Job(helper);
        Example<Job> companyExample = Example.of(job, exampleMatcher);
        PageRequest pageRequest = PageHelper.getPage(helper.getPage());
        Page<Job> page = jobRepository.findAll(JobSpecification.get(companyExample, helper), pageRequest);
        List<JobHelper> jobHelpers = page.getContent().stream().map(JobHelper::new).toList();
        return new PageImpl<>(jobHelpers, pageRequest, jobHelpers.size());
    }

    @Override
    public JobHelper create(CreateJobHelper createJobHelper) throws DefaultException {
        User loggedUser = JwtUtils.getLoggedUser();
        Company company = ((loggedUser.getRole().equals(UserRole.COMPANY))
                ? companyRepository.findByIdAndUserEmail(createJobHelper.getIdCompany(), loggedUser.getEmail())
                : companyRepository.findById(createJobHelper.getIdCompany()))
                .orElseThrow(() -> new DefaultException("Company with id " + createJobHelper.getIdCompany() + " has not exist."));
        Job job = new Job(createJobHelper, company);
        job = jobRepository.save(job);
        return new JobHelper(job);
    }

    @Override
    public JobHelper get(Long jobId) throws DefaultException {
        Job job = jobDao.getJob(jobId);
        return new JobHelper(job);
    }

    @Override
    public JobHelper edit(JobHelper jobHelper) throws DefaultException {
        if (jobHelper.getId() == null) throw new DefaultException("Please choose job to edit");
        Job job = jobDao.getJob(jobHelper.getId());
        //TODO Przygotuj osobny kontroler ze sprawdzeniem czy odpowiedni użytkownik jest z danej firmy
//        Company company = ((role.equals(UserRole.COMPANY))
//                ? companyRepository.findBy
//                : companyRepository.findById(companyHelper.getId()))
//                .orElseThrow(() -> new DefaultException("Company with id " + companyHelper.getId() + " has not exist."));
        job.fillUpFields(jobHelper);
        job = jobRepository.save(job);
        return new JobHelper(job);
    }

    @Override
    public void delete(Long jobId) throws DefaultException {
        Job job = jobDao.getJob(jobId);
        job.setStatus(JobStatus.DELETED);
        jobRepository.save(job);
    }

    public JobHelper changeStatus(Long jobId, JobStatus status) throws DefaultException {
        Job job = jobDao.getJob(jobId);
        job.setStatus(status);
        job = jobRepository.save(job);
        return new JobHelper(job);
    }
}
