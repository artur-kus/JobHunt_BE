package it.jobhunt.JobHunt.persistance;

import it.jobhunt.JobHunt.entity.Candidate;
import it.jobhunt.JobHunt.exception.DefaultException;

public interface CandidateDao {

    Candidate getCandidate(Long idCandidate) throws DefaultException;
}