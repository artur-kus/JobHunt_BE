package it.jobhunt.JobHunt.repository;

import it.jobhunt.JobHunt.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    @Query("SELECT c.jobs FROM Company c JOIN c.jobs j JOIN c.users u WHERE j.id =?1 AND u.email = ?2")
    Optional<Job> findByIdAndUserEmail(Long id, String email);

    @Query("FROM Job j JOIN FETCH j.responses WHERE j.id = ?1")
    Optional<Job> findByIdAndInitializeResponse(Long id);
}
