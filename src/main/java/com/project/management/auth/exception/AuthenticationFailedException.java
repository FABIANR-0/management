package com.project.management.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando falla la autenticación.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationFailedException extends RuntimeException {

    /**
     * Constructor de AuthenticationFailedException.
     *
     * @param message Mensaje de error.
     */
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
