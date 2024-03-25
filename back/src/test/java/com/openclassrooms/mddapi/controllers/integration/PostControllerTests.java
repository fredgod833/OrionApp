package com.openclassrooms.mddapi.controllers.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dtos.PostDTO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTests {

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
        PostControllerTests.userRepo = userRepo;

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
    public void testGetPosts() throws Exception {
        mockMvc.perform(get("/api/posts")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetPost() throws Exception {
        mockMvc.perform(get("/api/posts/{id}", 1)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testSavePost() throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Title");
        postDTO.setContent("Content");
        postDTO.setTheme("Java");

        mockMvc.perform(post("/api/posts")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("test_user"))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.theme").value("Java"));
    }
}
