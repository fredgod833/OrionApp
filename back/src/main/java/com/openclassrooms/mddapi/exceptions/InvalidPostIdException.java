package com.openclassrooms.mddapi.exceptions;

/**
 * Identifiant d'article invalide
 */
public class InvalidPostIdException extends Exception {

    public InvalidPostIdException(final String message) {
        super(message);
    }

}
