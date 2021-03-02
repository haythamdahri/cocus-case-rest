package com.cocus.microservices.label.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Haytham DAHRI
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class TechnicalException extends RuntimeException {

    private static final long serialVersionUID = -4531182805106573140L;

    public TechnicalException() {
        super("An internal server error occurred!");
    }

    public TechnicalException(String message) {
        super(message);
    }

}
