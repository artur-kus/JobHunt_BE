package it.jobhunt.JobHunt.service;

import it.jobhunt.JobHunt.entity.Candidate;
import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.entity.Response;
import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.InternalException;
import it.jobhunt.JobHunt.repository.CandidateRepository;
import it.jobhunt.JobHunt.repository.JobRepository;
import it.jobhunt.JobHunt.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CandidateRepository candidateRepository;


    public void sendResponseByLoggedUser(Long jobId) throws InternalException, DefaultException {
        User loggedUser = JwtUtils.getLoggedUser();
        Candidate candidate = candidateRepository.findByUserId(loggedUser.getId())
                .orElseThrow(() -> new InternalException("User " + loggedUser.getId() + " doesn't exist."));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new InternalException("Job " + jobId + " doesn't exist."));
        Response response = new Response(candidate);
        job.setResponses(List.of(response));
        jobRepository.save(job);
    }
}
