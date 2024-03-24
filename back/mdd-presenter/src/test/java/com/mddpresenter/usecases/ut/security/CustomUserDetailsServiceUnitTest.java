package com.mddpresenter.usecases.ut.security;

import com.mddcore.domain.models.User;
import com.mdddetails.repository.user.UserRepoImpl;
import com.mddinfrastructure.security.userdetails.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceUnitTest {

    @Mock
    private UserRepoImpl userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void loadUserByUsername_WithValidEmail_ShouldReturnUserDetails() {
        String email = "user@example.com";
        User user = mock(User.class);
        doReturn(email).when(user).getEmail();
        doReturn(Optional.of(user)).when(userRepository).findByEmail(email);

        UserDetails result = customUserDetailsService.loadUserByUsername(email);

        assertThat(result.getUsername()).isEqualTo(email);
    }

    @Test
    void loadUserByUsername_WithInvalidEmail_ShouldThrowUsernameNotFoundException() {
        String email = "nonexistent@example.com";
        doReturn(Optional.empty()).when(userRepository).findByEmail(email);

        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found with email");
    }

    @Test
    void loadUserById_WithValidId_ShouldReturnUserDetails() {
        Long userId = 1L;
        User user = mock(User.class);
        doReturn(Optional.of(user)).when(userRepository).findById(userId);

        UserDetails result = customUserDetailsService.loadUserById(userId);

        assertThat(result).isNotNull();
    }

    @Test
    void loadUserById_WithInvalidId_ShouldThrowUsernameNotFoundException() {
        Long userId = 99L;
        doReturn(Optional.empty()).when(userRepository).findById(userId);

        assertThatThrownBy(() -> customUserDetailsService.loadUserById(userId))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found with subscriptionId");
    }
}
