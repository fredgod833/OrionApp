package com.openclassrooms.mddapi.exceptions;

/**
 * Utilisateur non valide
 */
public class InvalidUserException extends Exception {
    public InvalidUserException(String message) {
        super(message);
    }
}
