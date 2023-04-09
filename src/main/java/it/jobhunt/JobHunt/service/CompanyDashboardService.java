package it.jobhunt.JobHunt.service;

import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.helper.job.JobHelper;
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
public class CompanyDashboardService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobRepository jobRepository;

    public Page<JobHelper> findAllJob(JobFilter helper) throws DefaultException {
        User loggedUser = JwtUtils.getLoggedUser();
        List<Long> companyIds = companyRepository.findAllIdsByUserEmail(loggedUser.getEmail());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIgnoreCase();
        helper.setIdCompanies(companyIds);
        Job job = new Job(helper);
        Example<Job> companyExample = Example.of(job, exampleMatcher);
        PageRequest pageRequest = PageHelper.getPage(helper.getPage());
        Page<Job> page = jobRepository.findAll(JobSpecification.get(companyExample, helper), pageRequest);
        List<JobHelper> jobHelpers = page.getContent().stream().map(JobHelper::new).toList();
        return new PageImpl<>(jobHelpers, pageRequest, jobHelpers.size());
    }
}
