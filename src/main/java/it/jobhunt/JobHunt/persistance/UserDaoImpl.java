package it.jobhunt.JobHunt.persistance;

import it.jobhunt.JobHunt.entity.User;
import it.jobhunt.JobHunt.enums.UserRole;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.security.SignupRequest;
import it.jobhunt.JobHunt.helper.user.UserHelper;
import it.jobhunt.JobHunt.repository.UserRepository;
import it.jobhunt.JobHunt.service.AuthService;
import it.jobhunt.JobHunt.util.GeneralUtil;
import it.jobhunt.JobHunt.util.JwtUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@NoArgsConstructor
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    public UserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User get(String email) throws DefaultException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
    }

    @Override
    public User getOrCreate(String email, String password, UserRole role) throws DefaultException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
           authService.register(new SignupRequest(email, password, role));
        }
        return get(email);
    }

    @Override
    public User changePassword(String password) throws DefaultException {
        User loggedUser = JwtUtils.getLoggedUser();
        loggedUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(loggedUser);
    }

    @Override
    public User changePassword(String email, String password) throws DefaultException {
        User user = get(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public User changeEmail(String email) throws DefaultException {
        User loggedUser = JwtUtils.getLoggedUser();
        loggedUser.setEmail(email);
        return userRepository.save(loggedUser);
    }

    @Override
    public User changeEmail(String actualEmail, String futureEmail) throws DefaultException {
        User user = get(actualEmail);
        user.setEmail(futureEmail);
        return userRepository.save(user);
    }

    @Override
    public void editData(String email, UserHelper helper) throws DefaultException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean userChanges = false;
            if (!GeneralUtil.isNullOrEmpty(helper.getEmail())) {
                user.setEmail(helper.getEmail());
                userChanges = true;
            } else if (!GeneralUtil.isNullOrEmpty(helper.getPassword())) {
                user.setPassword(passwordEncoder.encode(helper.getPassword()));
                userChanges = true;
            }
            if (userChanges) userRepository.save(user);
        }
    }
}
