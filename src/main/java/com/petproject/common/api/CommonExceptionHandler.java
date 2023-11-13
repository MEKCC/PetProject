package com.petproject.common.api;

import com.petproject.common.exception.NotFoundException;
import com.petproject.common.exception.RegistrationException;
import com.petproject.common.exception.UserException;
import com.petproject.common.logging.LogResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.petproject")
public class CommonExceptionHandler {
    @LogResponseBody
    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleNniImportException(final UserException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<Void> handleNotFoundExceptions(final NotFoundException exception) {
        final String exceptionMessage = exception.getMessage();
        if (exceptionMessage == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.notFound().header("Message", exceptionMessage).build();
        }
    }

    @LogResponseBody
    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<String> handleRegistrationException(final RegistrationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
