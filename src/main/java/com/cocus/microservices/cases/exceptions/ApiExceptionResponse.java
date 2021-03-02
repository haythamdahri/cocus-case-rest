package com.cocus.microservices.label.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

/**
 * @author Haytham DAHRI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiExceptionResponse {

    /**
     * Exception response message property
     */
    private String message;

    /**
     * Http Status
     */
    private HttpStatus httpStatus;

    /**
     * Http Status Code
     */
    private int statusCode;

    /**
     * Exception response time property
     */
    private LocalDateTime timestamp;

}
