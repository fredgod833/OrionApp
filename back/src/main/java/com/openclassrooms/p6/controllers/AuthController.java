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

            AuthResponse authResponse = new AuthResponse(jwtToken);

            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);

        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
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
            GlobalExceptionHandler.handleLogicError("Conflict", HttpStatus.CONFLICT);
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
            GlobalExceptionHandler.handleLogicError("Conflict", HttpStatus.CONFLICT);
        }
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return The user with the specified email.
     * @throws ApiException If the user with the given email does not exist.
     */
    private Users getUserByEmail(String email) {
        Optional<Users> optionalUser = userService.getUserByEmail(email);

        Boolean userNotFound = optionalUser.isEmpty();
        if (userNotFound) {
            GlobalExceptionHandler.handleLogicError("Not found", HttpStatus.NOT_FOUND);
        }

        return optionalUser.get();
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
            GlobalExceptionHandler.handleLogicError("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
}
