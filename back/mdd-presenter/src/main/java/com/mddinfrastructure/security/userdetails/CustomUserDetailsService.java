package com.mddinfrastructure.security.userdetails;

import com.mddcore.domain.models.User;
import com.mdddetails.repository.user.UserRepoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepoImpl userRepository;

    public CustomUserDetailsService(UserRepoImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        logger.info("CustomUserDetails, user after find by email : {}", user.toString());
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        logger.info("customUserDetails end : {} = ", customUserDetails);
        return customUserDetails;
    }

    @Transactional
    public UserDetails loadUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId : " + userId));
        return new CustomUserDetails(user);
    }
}
