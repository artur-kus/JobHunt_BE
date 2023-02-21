package it.jobhunt.JobHunt.persistance;

import it.jobhunt.JobHunt.entity.Candidate;
import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.enums.UserRole;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.repository.CandidateRepository;
import it.jobhunt.JobHunt.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CandidateDaoImpl implements CandidateDao {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Candidate getCandidate(Long idCandidate) throws DefaultException {
        User user = JwtUtils.getLoggedUser();
        UserRole role = User.getRole(user);
        return ((role.equals(UserRole.COMPANY))
                ? candidateRepository.findByIdAndUserEmail(idCandidate, user.getEmail())
                : candidateRepository.findById(idCandidate))
                .orElseThrow(() -> new DefaultException("Candidate with id " + idCandidate + " has not exist."));
    }
}
