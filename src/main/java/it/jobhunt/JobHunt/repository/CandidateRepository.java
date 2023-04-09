package it.jobhunt.JobHunt.repository;

import it.jobhunt.JobHunt.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query("FROM Candidate c WHERE c.id = ?1 AND c.user.email = ?2")
    Optional<Candidate> findByIdAndUserEmail(Long id, String email);

    @Query("FROM Candidate c WHERE c.user.id = ?1")
    Optional<Candidate> findByUserId(Long userId);
}