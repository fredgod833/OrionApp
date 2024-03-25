package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.requests.RegisterRequest;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTests {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepo;
    private ModelMapper modelMapper;
    private ThemeService themeService;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        userRepo = mock(UserRepository.class);
        themeService = mock(ThemeService.class);
        modelMapper = mock(ModelMapper.class);
        userService = new UserServiceImpl(passwordEncoder, userRepo, modelMapper, themeService);
    }

    @Test
    public void testRegister_Success() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("test_user");
        registerRequest.setPassword("Aa12345.");
        registerRequest.setEmail("test@example.com");

        User user = new User();
        user.setUsername("test_user");
        user.setPassword("Aa12345.");
        user.setEmail("test@example.com");

        when(modelMapper.map(registerRequest, User.class)).thenReturn(user);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("Aa12345.");

        userService.register(registerRequest);

        verify(userRepo, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode(registerRequest.getPassword());
        verify(modelMapper, times(1)).map(registerRequest, User.class);
    }

    @Test
    public void testRegister_Failure_UsernameExists() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("test_user");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("Aa12345.");

        when(userService.usernameDoesNotExist(registerRequest.getUsername())).thenReturn(false);
        when(userService.emailDoesNotExist(registerRequest.getEmail())).thenReturn(true);

        assertThrows(DuplicateKeyException.class, () -> userService.register(registerRequest));
        verify(userRepo, never()).save(any());
    }

    @Test
    public void testRegister_Failure_EmailExists() {
        RegisterRequest registerRequest = new RegisterRequest();

        when(userService.usernameDoesNotExist(registerRequest.getUsername())).thenReturn(true);
        when(userService.emailDoesNotExist(registerRequest.getEmail())).thenReturn(false);

        assertThrows(DuplicateKeyException.class, () -> userService.register(registerRequest));
        verify(userRepo, never()).save(any());
    }

    @Test
    public void testGetById_Success() {
        int userId = 1;
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("test_user");

        when(userRepo.findById(userId)).thenReturn(java.util.Optional.of(user));

        User retrievedUser = userService.getById(userId);

        verify(userRepo, times(1)).findById(userId);
        assertEquals(user, retrievedUser);
        assertEquals(user.getUsername(), retrievedUser.getUsername());
    }

    @Test
    public void testGetById_Failure_UserNotFound() {
        int userId = 1;
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("test_user");

        when(userRepo.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    public void testGetByEmail_Success() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("test_user");

        when(userRepo.findByEmail(email)).thenReturn(java.util.Optional.of(user));

        User retrievedUser = userService.getByEmail(email);

        verify(userRepo, times(1)).findByEmail(email);
        assertEquals(user, retrievedUser);
        assertEquals(user.getUsername(), retrievedUser.getUsername());
    }

    @Test
    public void testGetByEmail_Failure_UserNotFound() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("test_user");

        when(userRepo.findByEmail(email)).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getByEmail(email));
    }

    @Test
    public void testGetByUsername_Success() {
        String username = "test_user";
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("test_user");

        when(userRepo.findByUsername(username)).thenReturn(java.util.Optional.of(user));

        User retrievedUser = userService.getByUsername(username);

        verify(userRepo, times(1)).findByUsername(username);
        assertEquals(user, retrievedUser);
        assertEquals(user.getUsername(), retrievedUser.getUsername());
    }

    @Test
    public void testGetByUsername_Failure_UserNotFound() {
        String username = "test@example.com";
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("test_user");

        when(userRepo.findByUsername(username)).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getByUsername(username));
    }

    @Test
    public void testUpdate_Success() {
        int userId = 1;
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("updatedtest@example.com");
        userDTO.setUsername("updated_user");
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("test_user");

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(userRepo.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(userRepo.existsByUsername(userDTO.getUsername())).thenReturn(false);

        userService.update(userId, userDTO);

        assertEquals(userDTO.getUsername(), user.getUsername());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void testEmailDoesNotExist_Success() {
        String email = "test@example.com";

        when(userRepo.existsByEmail(email)).thenReturn(false);

        boolean emailExists = userService.emailDoesNotExist(email);

        assertTrue(emailExists);
    }

    @Test
    public void testEmailDoesNotExist_Failure_EmailExists() {
        String email = "test@example.com";

        when(userRepo.existsByEmail(email)).thenReturn(true);

        assertThrows(DuplicateKeyException.class, () -> userService.emailDoesNotExist(email));
    }

    @Test
    public void testUsernameDoesNotExist_Success() {
        String username = "test_user";

        when(userRepo.existsByUsername(username)).thenReturn(false);

        boolean emailExists = userService.usernameDoesNotExist(username);

        assertTrue(emailExists);
    }

    @Test
    public void testUsernameDoesNotExist_Failure_UsernameExists() {
        String username = "test_user";

        when(userRepo.existsByUsername(username)).thenReturn(true);

        assertThrows(DuplicateKeyException.class, () -> userService.usernameDoesNotExist(username));
    }

    @Test
    void testSubscribeToTheme_Success() {
        int themeId = 1;
        String email = "test@example.com";
        Theme theme = new Theme();
        theme.setId(themeId);
        theme.setName("Test theme name");
        theme.setDescription("Test theme description");
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("test_user");
        user.setSubscriptions(new ArrayList<>());

        when(themeService.getById(themeId)).thenReturn(theme);
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(userRepo.save(user)).thenReturn(user);

        User updatedUser = userService.subscribeToTheme(themeId, email);

        verify(themeService, times(1)).getById(themeId);
        verify(userRepo, times(1)).findByEmail(email);
        verify(userRepo, times(1)).save(user);
        assertEquals(1, updatedUser.getSubscriptions().size());
        assertTrue(updatedUser.getSubscriptions().contains(theme));
    }

    @Test
    void testUnSubscribeFromTheme_Success() {
        int themeId = 1;
        String email = "test@example.com";
        Theme theme = new Theme();
        theme.setId(themeId);
        theme.setDescription("Test theme description");
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("test_user");
        user.setSubscriptions(new ArrayList<>(List.of(theme)));

        when(themeService.getById(themeId)).thenReturn(theme);
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(userRepo.save(user)).thenReturn(user);

        User updatedUser = userService.unSubscribeFromTheme(themeId, email);

        verify(themeService, times(1)).getById(themeId);
        verify(userRepo, times(1)).findByEmail(email);
        verify(userRepo, times(1)).save(user);
        assertEquals(0, updatedUser.getSubscriptions().size());
    }
}
