package com.openclassrooms.mddapi.controllers.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.security.JWTGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static final User user = new User();
    private static String token;
    private static UserRepository userRepo;

    @BeforeAll
    public static void setup(@Autowired AuthenticationManager authenticationManager,
                             @Autowired JWTGenerator jwtGenerator,
                             @Autowired UserRepository userRepo,
                             @Autowired PasswordEncoder passwordEncoder) {
        UserControllerTests.userRepo = userRepo;

        user.setEmail("test@example.com");
        user.setUsername("test_user");
        user.setPassword(passwordEncoder.encode("Aa12345."));
        userRepo.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "test@example.com",
                        "Aa12345."));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        token = jwtGenerator.generateToken(authentication.getName());
    }

    @AfterAll
    public static void cleanUp() {
        userRepo.delete(user);
    }

    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/api/user")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.username").value("test_user"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = userRepo.findByEmail("test@example.com")
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername("update_user");

        mockMvc.perform(put("/api/user/update/{id}", user.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User updated successfully"));
    }

    @Test
    public void testSubscribe() throws Exception {
        mockMvc.perform(post("/api/user/subscribe/{id}", 1)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnsubscribe() throws Exception {
        mockMvc.perform(delete("/api/user/unsubscribe/{id}", 1)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }
}
