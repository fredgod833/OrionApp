package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.exceptions.InvalidUserRegistrationException;
import com.openclassrooms.mddapi.models.UserEntity;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.SignupRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtService;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    AuthController(AuthenticationManager authenticationManager,
                   PasswordEncoder passwordEncoder,
                   UserRepository userRepository, JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping(value="/login", produces = APPLICATION_JSON_VALUE)
    @Operation(summary="Log in Client with credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Log in successfull, bearer token returned.",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid form, bad request, user already exists. The reason is returned in message.",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )})
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtService.generateAccessToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAdmin()));
    }

    @PostMapping(value ="/register", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws InvalidUserRegistrationException {

        if (userRepository.existsByLoginOrEmail(signUpRequest.getEmail(), signUpRequest.getEmail())) {
            throw new InvalidUserRegistrationException("Un compte existe avec cet email ! Veuillez vous logger.");
        }

        if (userRepository.existsByLoginOrEmail(signUpRequest.getLogin(), signUpRequest.getLogin())) {
            throw new InvalidUserRegistrationException("Ce nom d'utilisateur est déjà pris, veuillez en choisir un autre.");
        }

        // Create new user's account
        UserEntity userEntity = new UserEntity( signUpRequest.getLogin(), signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(userEntity);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signUpRequest.getLogin(),
                        signUpRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtService.generateAccessToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAdmin()));
    }

    @GetMapping(value ="/me", produces = APPLICATION_JSON_VALUE)
    @Operation(summary="Get the connected user informations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Return the connected user informations.",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))
            ),
            @ApiResponse(responseCode = "401",
                    description = "Invalid connection, token expired, etc..",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )})
    @SecurityRequirement(name = "Bearer JWT Authentication")
    public ResponseEntity<JwtResponse> getConnectedUser(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return ResponseEntity.ok(new JwtResponse((String) auth.getCredentials(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAdmin()));
    }

}
