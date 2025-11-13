package com.openclassrooms.mddapi.exceptions;

/**
 * Identifiant de Theme invalide
 */
public class InvalidTopicIdException extends Exception {

    public InvalidTopicIdException(final String message) {
        super(message);
    }

}
