package com.openclassrooms.mddapi.exceptions;

/**
 * Identifiant de commentaire invalide
 */
public class InvalidCommentIdException extends Exception {

    public InvalidCommentIdException(final String message) {
        super(message);
    }

}
