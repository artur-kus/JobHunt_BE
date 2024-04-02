package it.jobhunt.JobHunt.persistance;

import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.enums.UserRole;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.NotFoundException;
import it.jobhunt.JobHunt.repository.JobRepository;
import it.jobhunt.JobHunt.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JobDaoImpl implements JobDao {

    private final JobRepository jobRepository;

    @Override
    public Job getJob(Long jobId) throws DefaultException, NotFoundException {
        User loggedUser = JwtUtils.getLoggedUser();
        UserRole role = User.getRole(loggedUser);
        return (role.equals(UserRole.COMPANY)
                ? jobRepository.findByIdAndUserEmail(jobId, loggedUser.getEmail())
                : jobRepository.findById(jobId))
                .orElseThrow(() -> new NotFoundException(Job.class));
    }
}
