package it.jobhunt.JobHunt.service;

import it.jobhunt.JobHunt.entity.Candidate;
import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.entity.Response;
import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.repository.CandidateRepository;
import it.jobhunt.JobHunt.repository.JobRepository;
import it.jobhunt.JobHunt.repository.ResponseRepository;
import it.jobhunt.JobHunt.util.DefaultResponse;
import it.jobhunt.JobHunt.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CandidateRepository candidateRepository;


    public DefaultResponse sendRepliedByLoggedUser(Long jobId) throws DefaultException {
        User loggedUser = JwtUtils.getLoggedUser();
        Candidate candidate = candidateRepository.findByUserId(loggedUser.getId())
                .orElseThrow(() -> new DefaultException("User " + loggedUser.getId() + " doesn't exist."));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new DefaultException("Job " + jobId + " doesn't exist."));
        Optional<Response> existResponse = responseRepository.findByCandidateIdAndJobId(candidate.getId(), jobId);
        if (existResponse.isPresent()) throw new DefaultException("You already responded to this offer");
        responseRepository.save(new Response(candidate, job));
        return DefaultResponse.builder()
                .message(String.format("Replied for %s , has been sent to company.", job.getName()))
                .httpStatus(HttpStatus.OK).build();
    }
}
