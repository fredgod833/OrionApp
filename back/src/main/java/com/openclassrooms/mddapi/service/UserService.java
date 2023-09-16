package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserRegisterDto;
import com.openclassrooms.mddapi.model.Role;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.RoleRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    public String defaultRole = "ROLE_USER"; // Change role if needed
    public int enabledUser = 1; // By default new user is enabled
    public PasswordEncoder passwordEncoder = passwordEncoder();

    public Authentication userAuthentication(
            String email,
            String password,
            HttpServletRequest req,
            AuthenticationManager authenticationManager
        ) {
            UsernamePasswordAuthenticationToken authReq =
                    new UsernamePasswordAuthenticationToken(email, password);
            Authentication auth = authenticationManager.authenticate(authReq);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
            HttpSession session = req.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
            return auth;
    }

    public void saveUser(UserRegisterDto userRegisterDto) {
        if (getUserByEmail(userRegisterDto.getEmail()) == null) {
            User user = userRepository.save(
                    createUser(userRegisterDto)
            );
            roleRepository.save(
                createRole(user)
            );
        }
    }

    private Role createRole(User user) {
        Role role = new Role();
        role.setUser(user);
        role.setAuthority(defaultRole);
        role.setEnabled(enabledUser);
        return role;
    }

    private User createUser(UserRegisterDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}