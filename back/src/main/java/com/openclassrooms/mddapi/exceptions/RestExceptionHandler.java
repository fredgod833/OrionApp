package com.openclassrooms.mddapi.exceptions;

import com.openclassrooms.mddapi.models.payload.response.MessageResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidRegistrationException.class)
    protected ResponseEntity<MessageResponse> handleStorageException(InvalidRegistrationException e) {
        MessageResponse apiResponse = new MessageResponse(e.getMessage());
        apiResponse.setMessage(e.getMessage());
        logger.info(e);
        return ResponseEntity
                .badRequest()
                .contentType(APPLICATION_JSON)
                .body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<MessageResponse> handleStorageException(Exception e) {
        MessageResponse apiResponse = new MessageResponse(e.getMessage());
        apiResponse.setMessage("exception inattendue.");
        logger.error("exception inattendue.", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .body(apiResponse);
    }

}
