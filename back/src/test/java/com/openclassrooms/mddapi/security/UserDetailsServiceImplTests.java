package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserDetailsServiceImplTests {

    @Mock
    private UserService userService;
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        userDetailsService = new UserDetailsServiceImpl(userService);
    }

    @Test
    void testLoadUserByUsername_Email_Success() {
        String email = "test@example.com";
        User user = new User();
        user.setId(1);
        user.setEmail(email);
        user.setUsername("test_user");
        user.setPassword("password");

        when(userService.getByEmail(email)).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_Username_Success() {
        String username = "test_user";
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setUsername(username);
        user.setPassword("password");

        when(userService.getByUsername(username)).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_Failure_NotFound() {
        String username = "nonexistent_user";

        when(userService.getByUsername(username)).thenThrow(new UsernameNotFoundException("User not found"));

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username));
        assertEquals("User not found", exception.getMessage());
    }
}
