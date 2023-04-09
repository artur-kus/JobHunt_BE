package it.jobhunt.JobHunt.service;

import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.enums.JobStatus;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.job.DashboardJobHelper;
import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.helper.job.JobHelper;
import it.jobhunt.JobHunt.repository.JobRepository;
import it.jobhunt.JobHunt.repository.specification.JobSpecification;
import it.jobhunt.JobHunt.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    private JobRepository jobRepository;

    public Page<DashboardJobHelper> findAllJobs(JobFilter helper) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIgnoreCase();
        if (helper == null) {
            helper = new JobFilter();
        }
        Job job = new Job(helper);
        job.setStatus(JobStatus.ACTIVE);
        Example<Job> companyExample = Example.of(job, exampleMatcher);
        PageRequest pageRequest = PageHelper.getPage(helper.getPage());
        Page<Job> page = jobRepository.findAll(JobSpecification.get(companyExample, helper), pageRequest);
        List<DashboardJobHelper> jobHelpers = page.getContent().stream().map(DashboardJobHelper::new).toList();
        return new PageImpl<>(jobHelpers, pageRequest, jobHelpers.size());
    }

    public JobHelper getJob(Long jobId) throws DefaultException {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new DefaultException("Job " + jobId + " doesn't exist."));
        return new JobHelper(job);
    }

    //TODO
    public void responseForJob() {
    }
}
