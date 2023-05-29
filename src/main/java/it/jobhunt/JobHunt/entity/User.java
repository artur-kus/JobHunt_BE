package it.jobhunt.JobHunt.entity;

import it.jobhunt.JobHunt.enums.UserRole;
import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.helper.user.UserHelper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(name = "ENABLED")
    private Boolean enabled;


    public User(Long id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public User(UserHelper userHelper) {
        this.id = userHelper.getId();
        this.email = userHelper.getEmail();
        this.password = userHelper.getPassword();
        this.role = userHelper.getRole();
        this.enabled = userHelper.getEnabled();
    }

    public static UserRole getRole(User user) throws DefaultException {
        if (user == null) throw new DefaultException("Invalid user");
        if (user.getRole() != null) {
            return user.getRole();
        } else throw new DefaultException("User don't have any role");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
