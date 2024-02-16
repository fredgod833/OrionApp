package com.openclassrooms.p6.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.p6.exception.ApiException;
import com.openclassrooms.p6.exception.GlobalExceptionHandler;
import com.openclassrooms.p6.model.Subscriptions;
import com.openclassrooms.p6.model.Users;
import com.openclassrooms.p6.payload.request.SubscriptionToggleRequest;
import com.openclassrooms.p6.payload.request.UserRequest;
import com.openclassrooms.p6.payload.response.MessageResponse;
import com.openclassrooms.p6.service.UserService;
import com.openclassrooms.p6.utils.JwtUtil;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UserService userService;

    @PutMapping("")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest request,
            BindingResult bindingResult,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            checkBodyPayloadErrors(bindingResult);

            Long userId = verifyUserValidityFromToken(authorizationHeader);

            Users user = getVerifiedUserById(userId);

            Optional<Users> userFromRequestUsername = userService.getUserByUsername(request.username());

            Boolean usernameIsAlreadyTaken = userFromRequestUsername.isPresent()
                    && userFromRequestUsername.get().getUsername() != user.getUsername();
            if (usernameIsAlreadyTaken) {
                MessageResponse response = new MessageResponse(
                        "The new username is already taken !");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            Optional<Users> userFromRequestEmail = userService.getUserByEmail(request.email());

            Boolean emailIsAlreadyTaken = userFromRequestEmail.isPresent()
                    && userFromRequestEmail.get().getEmail() != user.getEmail();
            if (emailIsAlreadyTaken) {
                MessageResponse response = new MessageResponse(
                        "The new email is already taken !");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            user.setUsername(request.username());
            user.setEmail(request.email());

            userService.saveUser(user);

            MessageResponse response = new MessageResponse("Successfully changed the user credentials!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
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
     * Retrieves a user by their ID and verifies their existence.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the given ID.
     * @throws ApiException if the user with the given ID does not exist.
     */
    private Long verifyUserValidityFromToken(String authorizationHeader) {
        String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

        // Extract user ID from JWT
        Optional<Long> optionalUserIdFromToken = JwtUtil.extractUserId(jwtToken);

        Boolean hasJwtExtractionError = optionalUserIdFromToken.isEmpty();
        if (hasJwtExtractionError) {
            GlobalExceptionHandler.handleLogicError("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Long userId = optionalUserIdFromToken.get();

        getVerifiedUserById(userId);

        return userId;
    }

    /**
     * Retrieves a user by their ID and verifies their existence.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the given ID.
     * @throws ApiException if the user with the given ID does not exist.
     */
    private Users getVerifiedUserById(Long userId) {
        Optional<Users> optionalSpecificUser = userService.getUserById(userId);

        Boolean userWithIdDoesNotExist = optionalSpecificUser.isEmpty();
        if (userWithIdDoesNotExist) {
            GlobalExceptionHandler.handleLogicError("Not found",
                    HttpStatus.NOT_FOUND);
        }

        return optionalSpecificUser.get();
    }
}
