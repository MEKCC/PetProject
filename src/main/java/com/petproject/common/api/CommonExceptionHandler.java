package com.petproject.common.api;

import com.petproject.common.logging.LogResponseBody;
import com.petproject.common.model.NotFoundException;
import com.petproject.common.model.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.petproject.controller")
public class CommonExceptionHandler {

    @LogResponseBody
    @ExceptionHandler(UserException.class)
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> handleNniImportException(final UserException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
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
}
