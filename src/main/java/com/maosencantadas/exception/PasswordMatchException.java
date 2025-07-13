package com.maosencantadas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Resource found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Senhas não são iguais")
public class PasswordMatchException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Resource found exception.
     *
     * @param message the message
     */
    public PasswordMatchException(final String message) {
        super(message);
    }

}