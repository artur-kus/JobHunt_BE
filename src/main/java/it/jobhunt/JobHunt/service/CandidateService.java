package it.jobhunt.JobHunt.service;

import it.jobhunt.JobHunt.entity.Candidate;
import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.NotFoundException;
import it.jobhunt.JobHunt.helper.candidate.CandidateFilter;
import it.jobhunt.JobHunt.helper.candidate.CandidateHelper;
import it.jobhunt.JobHunt.helper.candidate.CreateCandidateHelper;
import it.jobhunt.JobHunt.helper.security.SignupRequest;
import it.jobhunt.JobHunt.persistance.CandidateDao;
import it.jobhunt.JobHunt.persistance.UserDao;
import it.jobhunt.JobHunt.repository.CandidateRepository;
import it.jobhunt.JobHunt.repository.UserRepository;
import it.jobhunt.JobHunt.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService implements CrudOperation<Candidate, CandidateHelper, CreateCandidateHelper, CandidateFilter> {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CandidateDao candidateDao;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @Override
    public Page<CandidateHelper> findAll(CandidateFilter candidateFilter) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIgnoreCase();
        if (candidateFilter == null) {
            candidateFilter = new CandidateFilter();
        }
        Candidate candidate = new Candidate(candidateFilter);
        Example<Candidate> companyExample = Example.of(candidate, exampleMatcher);
        Page<Candidate> page = candidateRepository.findAll(companyExample, PageHelper.getPage(candidateFilter.getPage()));
        List<CandidateHelper> companyHelpers = page.getContent().stream().map(CandidateHelper::new).toList();
        return new PageImpl<>(companyHelpers, page.getPageable(), companyHelpers.size());
    }

    @Override
    public CandidateHelper create(CreateCandidateHelper createCandidateHelper) throws DefaultException {
        Candidate candidate = new Candidate(createCandidateHelper);
        authService.register(new SignupRequest(createCandidateHelper.getUser()));
        User user = userDao.get(createCandidateHelper.getUser().getEmail());
        candidate.setUser(user);
        return new CandidateHelper(candidateRepository.save(candidate));
    }

    @Override
    public CandidateHelper get(Long candidateId) throws NotFoundException {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new NotFoundException(Candidate.class));
        return new CandidateHelper(candidate);
    }

    @Override
    public CandidateHelper edit(CandidateHelper candidateHelper) throws DefaultException, NotFoundException {
        if (candidateHelper.getId() == null) throw new NotFoundException(Candidate.class);
        Candidate candidate = candidateDao.getCandidate(candidateHelper.getId());
        candidate.fillUpFields(candidateHelper);
        if (candidateHelper.getUser() != null && candidate.getUser().getEmail() != null) {
            userDao.editData(candidate.getUser().getEmail(), candidateHelper.getUser());
        }
        candidate = candidateRepository.save(candidate);
        return new CandidateHelper(candidate);
    }

    @Override
    public void delete(Long id) throws DefaultException, NotFoundException {
        Candidate candidate = candidateDao.getCandidate(id);
        User user = candidate.getUser();
        userRepository.delete(user);
        candidateRepository.delete(candidate);
    }
}