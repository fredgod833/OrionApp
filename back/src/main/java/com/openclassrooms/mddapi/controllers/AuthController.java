package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.exceptions.InvalidRegistrationException;
import com.openclassrooms.mddapi.models.entities.UserEntity;
import com.openclassrooms.mddapi.models.payload.request.LoginRequest;
import com.openclassrooms.mddapi.models.payload.request.SignupRequest;
import com.openclassrooms.mddapi.models.payload.request.UserRequest;
import com.openclassrooms.mddapi.models.payload.response.JwtResponse;
import com.openclassrooms.mddapi.models.payload.response.MessageResponse;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}");

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
        return authenticate(loginRequest.getLogin(), loginRequest.getPassword());
    }

    private ResponseEntity<JwtResponse> authenticate(String loginRequest, String pwd) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest,
                        pwd));

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
    public ResponseEntity<JwtResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws InvalidRegistrationException {

        if (userRepository.existsByLoginOrEmail(signUpRequest.getEmail(), signUpRequest.getEmail())) {
            throw new InvalidRegistrationException("Un compte existe avec cet email ! Veuillez vous logger.");
        }

        if (userRepository.existsByLoginOrEmail(signUpRequest.getLogin(), signUpRequest.getLogin())) {
            throw new InvalidRegistrationException("Ce nom d'utilisateur est déjà pris, veuillez en choisir un autre.");
        }

        Matcher m = PASSWORD_PATTERN.matcher(signUpRequest.getPassword());
        if (!m.find()) {
            throw new InvalidRegistrationException("Le mot de passe de plus de 7 caractères doit contenir majuscule, minuscule et caractère spécial.");
        }

        // Create new user's account
        UserEntity userEntity = new UserEntity( signUpRequest.getLogin(), signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(userEntity);

        return authenticate(signUpRequest.getLogin(), signUpRequest.getPassword());
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

    @PutMapping(value ="/me", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> updateUser(Authentication auth, @Valid @RequestBody UserRequest userRequest) throws  InvalidRegistrationException{

        //Recherche de l'utilisateur connecté à modifier
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        try {
            //Verification que le mot de passe du formulaire est bien celui du user connecté
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            userRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidRegistrationException("Mot de passe erroné.",e);
        }

        // verif d'unicité du nouvel email si modifié
        if (!userDetails.getEmail().equalsIgnoreCase(userRequest.getEmail())) {
            // l'email a été modifié, on recherche si il existe un doublon en base
            if (userRepository.existsByLoginOrEmail(userRequest.getEmail(), userRequest.getEmail())) {
                throw new InvalidRegistrationException("Un compte existe avec cet email ! Veuillez vous logger.");
            }

        }

        // verif d'unicité du nouveau login si modifié
        if (!userDetails.getUsername().equalsIgnoreCase(userRequest.getLogin())) {
            // l'email a été modifié, on recherche si il existe un doublon en base
            if (userRepository.existsByLoginOrEmail(userRequest.getLogin(), userRequest.getLogin())) {
                throw new InvalidRegistrationException("Ce nom d'utilisateur est déjà pris, veuillez en choisir un autre.");
            }
        }

        int userId = userDetails.getId();
        UserEntity user =  this.userRepository.findById(userId).orElseThrow(() -> new InvalidRegistrationException("utilisateur non trouvé"));
        user.setEmail(userRequest.getEmail());
        user.setLogin(userRequest.getLogin());
        userRepository.save(user);

        return authenticate(userRequest.getLogin(), userRequest.getPassword());
    }

}