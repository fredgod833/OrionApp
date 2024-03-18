package com.mddinfrastructure.security.userdetails;

import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.IUserRepository;
import com.mdddetails.repository.user.UserRepoImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    public CustomUserDetailsService(UserRepoImpl userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user details by username (email).
     *
     * @param email The email of the user.
     * @return UserDetails object representing the user.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        return new CustomUserDetails(user);
    }

    /**
     * Loads user details by user ID.
     *
     * @param userId The ID of the user.
     * @return UserDetails object representing the user.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Transactional
    public UserDetails loadUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with subscriptionId : " + userId));
        return new CustomUserDetails(user);
    }
}
