package it.jobhunt.JobHunt.persistance;

import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.NotFoundException;

public interface JobDao {

    Job getJob(Long idJob) throws DefaultException, NotFoundException;
}