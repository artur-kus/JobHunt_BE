package it.jobhunt.JobHunt.repository;

import it.jobhunt.JobHunt.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    Optional<Response> findByIdAndCvFilePathIsNotNull(Long id);
    Optional<Response> findByCandidateIdAndJobId(Long candidateId, Long jobId);
}