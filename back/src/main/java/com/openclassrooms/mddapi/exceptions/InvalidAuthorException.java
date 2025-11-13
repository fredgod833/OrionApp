package com.openclassrooms.mddapi.exceptions;

/**
 * Exception décrivant que l'auteur n'est pas autorisé à faire une modification
 */
public class InvalidAuthorException extends Exception {

    public InvalidAuthorException(final String message) {
        super(message);
    }

}
