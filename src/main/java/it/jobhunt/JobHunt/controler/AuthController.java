package it.jobhunt.JobHunt.controler;

import it.jobhunt.JobHunt.helper.security.LoginRequest;
import it.jobhunt.JobHunt.helper.security.SignupRequest;
import it.jobhunt.JobHunt.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest request) {
        try {
            return new ResponseEntity<>(authService.register(request), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error while register user - " + request.getEmail() + ", error message: " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            return new ResponseEntity<>(authService.loginUser(loginRequest), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error while login user - " + loginRequest.getEmail() + ", error message: " + ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
