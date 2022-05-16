package com.salle.server.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(SlErrorException.class)
    @ResponseBody
    public ResponseEntity<String> handleZtErrorException(SlErrorException ex) {
        return new ResponseEntity<>(ex.getErrorMessage(), ex.getErrorHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleErrorException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
