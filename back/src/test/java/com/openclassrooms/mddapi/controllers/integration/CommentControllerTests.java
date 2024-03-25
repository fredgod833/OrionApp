package com.openclassrooms.mddapi.controllers.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dtos.CommentDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static String token;
    private static UserRepository userRepo;
    private static final User user = new User();

    @BeforeAll
    public static void setup(@Autowired AuthenticationManager authenticationManager,
                             @Autowired JWTGenerator jwtGenerator,
                             @Autowired UserRepository userRepo,
                             @Autowired PasswordEncoder passwordEncoder) {
        CommentControllerTests.userRepo = userRepo;

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
    public void testGetComment() throws Exception {
        mockMvc.perform(get("/api/post/{id}/comments", 1)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthor(user.getUsername());
        commentDTO.setContent("This is a comment");
        mockMvc.perform(post("/api/post/{id}", 1)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("This is a comment"));
    }
}
