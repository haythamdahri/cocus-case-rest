package com.cocus.microservices.label.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * @author Haytham DAHRI
 * API Excpetions handler class
 */
@RestControllerAdvice
public class ApiExceptionsHandlerAdvice {

    /**
     * BusinessException handler method
     */
    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ApiExceptionResponse> handle(BusinessException ex) {
        // Create Payload
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(ex.getMessage(),
                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        // return response
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiExceptionResponse);
    }

    /**
     * TechnicalException handler method
     */
    @ExceptionHandler(value = {TechnicalException.class})
    public ResponseEntity<ApiExceptionResponse> handle(TechnicalException ex) {
        // Create Payload
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        // return response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiExceptionResponse);
    }

    /**
     * NotFoundException handler method
     */
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ApiExceptionResponse> handle(NotFoundException ex) {
        // Create Payload
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(ex.getMessage(),
                HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        // return response
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiExceptionResponse);
    }

    /**
     * DisabledException handler method
     */
    @ExceptionHandler(value = {DisabledException.class})
    public ResponseEntity<ApiExceptionResponse> handle(DisabledException ex) {
        // Create Payload
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(ex.getMessage(),
                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        // return response
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiExceptionResponse);
    }

    /**
     * BadCredentialsException handler method
     */
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ApiExceptionResponse> handle(BadCredentialsException ex) {
        // Create Payload
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(ex.getMessage(),
                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        // return response
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiExceptionResponse);
    }

}
