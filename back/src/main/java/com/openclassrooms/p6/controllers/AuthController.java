package com.openclassrooms.p6.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.p6.exception.ApiException;
import com.openclassrooms.p6.exception.GlobalExceptionHandler;
import com.openclassrooms.p6.mapper.UserMapper;
import com.openclassrooms.p6.model.Users;
import com.openclassrooms.p6.payload.request.LoginRequest;
import com.openclassrooms.p6.payload.request.RegisterRequest;
import com.openclassrooms.p6.payload.response.AuthResponse;
import com.openclassrooms.p6.payload.response.UserInfoResponse;
import com.openclassrooms.p6.service.UserService;
import com.openclassrooms.p6.utils.JwtUtil;

import jakarta.validation.Valid;

/**
 * Controller for handling authentication-related operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * Registers a new user.
     *
     * @param request The registration request containing user details.
     * @return ResponseEntity<AuthResponse> A JWT if registration is successful.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request,
            BindingResult bindingResult) {
        try {
            checkBodyPayloadErrors(bindingResult);

            checkIfUsernameIsInUse(request.username());

            checkIfEmailIsInUse(request.email());

            Users user = userService.saveUserBySignUp(request);

            UserInfoResponse userEntity = userMapper.toDtoUser(user);

            String jwtToken = JwtUtil.generateJwtToken(userEntity.id());

            AuthResponse authResponse = new AuthResponse(jwtToken, userEntity.id(), userEntity.username(),
                    userEntity.email());

            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);

        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
    }

    /**
     * Logs in an existing user.
     *
     * @param request The login request containing user credentials.
     * @return ResponseEntity<AuthResponse> A JWT if login is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        try {
            checkBodyPayloadErrors(bindingResult);

            Users user = getUserFromIdentifier(request.identifier());

            checkUserPassword(request.password(), user);

            UserInfoResponse userEntity = userMapper.toDtoUser(user);

            String jwtToken = JwtUtil.generateJwtToken(userEntity.id());

            AuthResponse authResponse = new AuthResponse(jwtToken, userEntity.id(), userEntity.username(),
                    userEntity.email());

            return ResponseEntity.status(HttpStatus.OK).body(authResponse);
        } catch (ApiException ex) {
            return GlobalExceptionHandler.handleApiException(ex);
        }
    }

    /**
     * Checks if there are any payload errors in the request body.
     *
     * @param bindingResult The BindingResult object that holds the validation
     *                      errors.
     */
    private void checkBodyPayloadErrors(BindingResult bindingResult) {
        Boolean payloadIsInvalid = bindingResult.hasErrors();
        if (payloadIsInvalid) {
            GlobalExceptionHandler.handlePayloadError("Bad request", bindingResult, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves the user ID from the authorization header.
     *
     * @param authorizationHeader The authorization header containing the JWT token.
     * @return The user ID extracted from the JWT token.
     */
    private Long getUserIdFromAuthorizationHeader(String authorizationHeader) {
        String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

        // Extract user ID from JWT
        Optional<Long> optionalUserIdFromToken = JwtUtil.extractUserId(jwtToken);

        Boolean hasJwtExtractionError = optionalUserIdFromToken.isEmpty();
        if (hasJwtExtractionError) {
            GlobalExceptionHandler.handleLogicError("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        return optionalUserIdFromToken.get();
    }

    /**
     * Retrieves the user information as a DTO entity based on the user ID extracted
     * from the JWT
     * token.
     * 
     * @param userIdFromToken The user ID extracted from the JWT token.
     * @return The user information as a UserInfoResponse object.
     * @throws ApiException If the user with the given ID does not exist or if there
     *                      is a mismatch between the user ID and the token.
     */
    private UserInfoResponse verifyAndGetUserByTokenId(Long userIdFromToken) {
        // Fetch user information based on the user ID
        Optional<Users> optionalSpecificUser = userService.getUserById(userIdFromToken);
        Boolean userWithIdDoesNotExist = optionalSpecificUser.isEmpty();
        if (userWithIdDoesNotExist) {
            GlobalExceptionHandler.handleLogicError("Not found",
                    HttpStatus.NOT_FOUND);
        }

        Users user = optionalSpecificUser.get();
        // Convert user information to DTO
        UserInfoResponse userEntity = userMapper.toDtoUser(user);

        return userEntity;
    }

    /**
     * Checks if an username is already registered in the system.
     *
     * @param username The username to check if it is already registered.
     */
    private void checkIfUsernameIsInUse(String username) {
        Boolean hasAlreadyRegistered = userService.isUsernameInUse(username);
        if (hasAlreadyRegistered) {
            GlobalExceptionHandler.handleLogicError("Username is already in use", HttpStatus.CONFLICT);
        }
    }

    /**
     * Checks if an email is already registered in the system.
     *
     * @param email The email to check if it is already registered.
     */
    private void checkIfEmailIsInUse(String email) {
        Boolean hasAlreadyRegistered = userService.isEmailInUse(email);
        if (hasAlreadyRegistered) {
            GlobalExceptionHandler.handleLogicError("Email is already in use", HttpStatus.CONFLICT);
        }
    }

    /**
     * Checks if the provided password is correct for the given user.
     *
     * @param requestPassword The password to check.
     * @param user            The user to check the password against.
     */
    private void checkUserPassword(String requestPassword, Users user) {
        Boolean passwordIsIncorrect = !userService.isPasswordValid(requestPassword, user);
        if (passwordIsIncorrect) {
            GlobalExceptionHandler.handleLogicError("Password is incorrect", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Checks if the provided username or email in the login request is valid and
     * returns the user
     *
     * @param usernameOrEmail The username or email to check.
     * @throws ApiException If the username or email is invalid.
     */
    private Users getUserFromIdentifier(String usernameOrEmail) {
        Optional<Users> userFromEmail = userService.getUserByEmail(usernameOrEmail);
        Optional<Users> userFromUsername = userService.getUserByUsername(usernameOrEmail);

        Boolean identifierIsInvalid = userFromEmail.isEmpty() && userFromUsername.isEmpty();
        if (identifierIsInvalid) {
            GlobalExceptionHandler.handleLogicError("Invalid username/email", HttpStatus.UNAUTHORIZED);
        }

        return userFromEmail.isPresent() ? userFromEmail.get() : userFromUsername.get();
    }
}
