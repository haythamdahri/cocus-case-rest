package com.cocus.microservices.label.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Haytham DAHRI
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Input Object")
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -4531182805106573140L;

    public BusinessException(String message) {
        super(message);
    }

}
