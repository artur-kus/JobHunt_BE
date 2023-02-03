package it.jobhunt.JobHunt.repository;

import it.jobhunt.JobHunt.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("FROM Company c JOIN FETCH c.users u WHERE c.id = ?1 AND u.email = ?2")
    Optional<Company> findByIdAndUserEmail(Long id, String email);

    @Query("FROM Company c JOIN FETCH c.users u WHERE u.email = ?1")
    List<Company> findAllByUserEmail(String email);

    @Query("SELECT c.id FROM Company c INNER JOIN c.users u WHERE u.email = ?1")
    List<Long> findAllIdsByUserEmail(String email);
}
