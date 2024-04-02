package it.jobhunt.JobHunt.persistance;

import it.jobhunt.JobHunt.entity.Candidate;
import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.enums.UserRole;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.NotFoundException;
import it.jobhunt.JobHunt.repository.CandidateRepository;
import it.jobhunt.JobHunt.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CandidateDaoImpl implements CandidateDao {

    private final CandidateRepository candidateRepository;

    @Override
    public Candidate getCandidate(Long idCandidate) throws NotFoundException, DefaultException {
        User user = JwtUtils.getLoggedUser();
        UserRole role = User.getRole(user);
        return ((role.equals(UserRole.COMPANY))
                ? candidateRepository.findByIdAndUserEmail(idCandidate, user.getEmail())
                : candidateRepository.findById(idCandidate))
                .orElseThrow(() -> new NotFoundException(Candidate.class));
    }
}