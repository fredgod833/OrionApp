package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.mappers.UserMapper;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.responses.MessageResponse;
import com.openclassrooms.mddapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTests {

    private UserController controller;
    private UserService userService;
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        controller = new UserController(userService, userMapper);
    }

    @Test
    public void testGetUser_Success() throws UserNotFoundException {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");

        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        when(userService.getByEmail("test@example.com")).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setEmail("test@example.com");
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = controller.getUser(principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void testGetUser_UserNotFound() throws UserNotFoundException {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");

        when(userService.getByEmail("test@example.com")).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> response = controller.getUser(principal);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateUser_Success() {
        int userId = 1;
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");

        doNothing().when(userService).update(userId, userDTO);

        ResponseEntity<MessageResponse> response = controller.updateUser(userId, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        int userId = 1;
        doThrow(new UsernameNotFoundException("User not found")).when(userService).update(userId, new UserDTO());

        ResponseEntity<MessageResponse> response = controller.updateUser(userId, new UserDTO());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateUser_DuplicateKeyException() {
        int userId = 1;
        doThrow(new DuplicateKeyException("Duplicate email")).when(userService).update(userId, new UserDTO());

        ResponseEntity<MessageResponse> response = controller.updateUser(userId, new UserDTO());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Duplicate email", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    public void testSubscribe_Success() {
        UserDetails userDetails = mock(UserDetails.class);
        int themeId = 1;
        String username = "username";
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        UserDTO expectedDTO = new UserDTO();
        expectedDTO.setId(1);
        expectedDTO.setEmail("test@example.com");

        when(userService.subscribeToTheme(themeId, username)).thenReturn(user);
        when(userMapper.toDTO(user, username)).thenReturn(expectedDTO);
        when(userDetails.getUsername()).thenReturn(username);

        ResponseEntity<UserDTO> response = controller.subscribe(themeId, userDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
    }

    @Test
    public void testSubscribe_NotFound() {
        int themeId = 1;
        String username = "username";
        when(userService.subscribeToTheme(themeId, username))
                .thenThrow(new ResourceNotFoundException("Theme not found"));

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);

        ResponseEntity<UserDTO> response = controller.subscribe(themeId, userDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSubscribe_BadRequest() {
        int themeId = 1;
        String username = "username";
        Mockito.when(userService.subscribeToTheme(themeId, username))
                .thenThrow(new IllegalArgumentException("Invalid subscription"));

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);

        ResponseEntity<UserDTO> response = controller.subscribe(themeId, userDetails);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUnSubscribe_Success() {
        UserDetails userDetails = mock(UserDetails.class);
        int themeId = 1;
        String username = "username";
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        UserDTO expectedDTO = new UserDTO();
        expectedDTO.setId(1);
        expectedDTO.setEmail("test@example.com");

        when(userDetails.getUsername()).thenReturn(username);
        when(userService.unSubscribeFromTheme(themeId, username)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(expectedDTO);

        ResponseEntity<UserDTO> response = controller.unSubscribe(themeId, userDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());
    }
}
