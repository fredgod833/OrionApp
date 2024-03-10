package com.openclassrooms.mddapi.exceptions;


public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(int userId) {
        super("User with ID " + userId + " not found");
    }

    public UserNotFoundException(String email) {
        super("User with email " + email + " not found");
    }
}
