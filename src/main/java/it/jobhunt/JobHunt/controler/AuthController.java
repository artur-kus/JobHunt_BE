package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.exception.NotFoundException;
import it.jobhunt.JobHunt.helper.security.LoginRequest;
import it.jobhunt.JobHunt.helper.security.SignupRequest;
import it.jobhunt.JobHunt.helper.signUp.RegisterCandidate;
import it.jobhunt.JobHunt.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.OK);
    }

    @PostMapping("/signup/candidate")
    public ResponseEntity<?> registerCandidate(@Valid @RequestBody RegisterCandidate request) throws NotFoundException {
        return new ResponseEntity<>(authService.registerCandidate(request), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.loginUser(loginRequest), HttpStatus.OK);
    }
}
