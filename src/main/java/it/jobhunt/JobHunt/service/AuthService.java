package it.jobhunt.JobHunt.service;

import com.sun.jdi.InternalException;
import it.jobhunt.JobHunt.entity.Candidate;
import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.enums.UserRole;
import it.jobhunt.JobHunt.exception.NotFoundException;
import it.jobhunt.JobHunt.helper.security.LoginRequest;
import it.jobhunt.JobHunt.helper.security.LoginResponse;
import it.jobhunt.JobHunt.helper.security.SignupRequest;
import it.jobhunt.JobHunt.helper.signUp.RegisterCandidate;
import it.jobhunt.JobHunt.repository.CandidateRepository;
import it.jobhunt.JobHunt.repository.UserRepository;
import it.jobhunt.JobHunt.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private CandidateRepository candidateRepository;

    public LoginResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        return new LoginResponse(user, jwtUtils.generateToken(user));
    }

    public String register(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new InternalException("User with email: " + request.getEmail() + " has been exist.");
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getUserRole())
                .enabled(true)
                .build();
        userRepository.save(user);
        return jwtUtils.generateToken(user);
    }

    public String registerCandidate(RegisterCandidate request) throws NotFoundException {
        Candidate candidate = new Candidate(request);
        String token = register(new SignupRequest(request.getEmail(), request.getPassword(), UserRole.USER));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException(User.class));
        candidate.setUser(user);
        candidateRepository.save(candidate);
        return token;
    }
}
