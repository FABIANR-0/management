package com.project.management.common.exception_handler;

import com.project.management.auth.exception.AuthenticationFailedException;
import com.project.management.auth.exception.RefreshTokenException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que maneja las excepciones de la aplicación y proporciona respuestas de error adecuadas.
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class AppExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    /**
     * Maneja la excepción de recurso no encontrado.
     *
     * @param exception la excepción ResourceNotFoundException
     * @return un mapa con el mensaje de error y el mensaje de la excepción
     */
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Not found");
        map.put("message", exception.getMessage());
        log.error("ResourceNotFoundException {}", exception.getMessage());
        return map;
    }

    /**
     * Maneja la excepción de argumento ilegal.
     *
     * @param exception la excepción IllegalArgumentException
     * @return un mapa con el mensaje de error y el mensaje de la excepción
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Bad Request");
        map.put("message", exception.getMessage());
        log.error("IllegalArgumentException {}", exception.getMessage());
        return map;
    }

    /**
     * Maneja la excepción de autenticación fallida.
     *
     * @param exception la excepción AuthenticationFailedException
     * @return un mapa con el mensaje de error y el mensaje de la excepción
     */
    @ExceptionHandler(value = AuthenticationFailedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleAuthenticationException(AuthenticationFailedException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Authentication failed");
        map.put("message", exception.getMessage());
        log.error("AuthenticationFailedException {}", exception.getMessage());
        return map;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(Exception exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Error");
        map.put("message", "Estamos teniendo problemas. Intente más tarde.");
        log.error("Exception {}", exception.getMessage());
        return map;
    }

    /**
     * Maneja la excepción de acceso denegado.
     *
     * @param exception la excepción AccessDeniedException
     * @return un mapa con el mensaje de error y el nombre del permiso
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleAccessDeniedException(AccessDeniedException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Acceso denegado");
        map.put("permission_name", exception.getMessage());
        log.error("AccessDeniedException {}", exception.getMessage());
        return map;
    }

    /**
     * Maneja la excepción de actualización de token fallida.
     *
     * @param exception la excepción RefreshTokenException
     * @return un mapa con el mensaje de error y el mensaje de la excepción
     */
    @ResponseBody
    @ExceptionHandler(value = RefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleTokenRefreshException(RefreshTokenException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Refresh token");
        map.put("message", "Acceso no autorizado");
        log.error("RefreshTokenException {}", exception.getMessage());
        return map;
    }

    /**
     * Maneja la excepción de validación de argumentos de método no válidos.
     * @param ex
     * @return una lista de respuestas de error de campo
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleExceptionBadRequest(MethodArgumentNotValidException ex) {
        Map<String, String> map = new HashMap<>();
        if (ex.getBindingResult().getFieldError() != null) {
            map.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());
            log.error("MethodArgumentNotValidException {}", ex.getBindingResult().getFieldError().getDefaultMessage());
            return map;
        }
        map.put("message", ex.getBindingResult().getGlobalError().getDefaultMessage());
        log.error("MethodArgumentNotValidException {}", ex.getBindingResult().getGlobalError().getDefaultMessage());
        return map;
    }

    /**
     * Maneja errores de validación al recibir listas con elementos validados
     * @param ex
     * @return un mapa con el mensaje de la excepción
     */
    @ExceptionHandler(value = HandlerMethodValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handlerMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, String> map = new HashMap<>();
        ex.getAllValidationResults().forEach(validationResult ->
                validationResult.getResolvableErrors().forEach(error -> {
                    map.put("message", error.getDefaultMessage());
                    log.error("HandlerMethodValidationException {}", error.getDefaultMessage());
                })
        );
        return map;
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> constraintViolationException(ConstraintViolationException ex) {
        Map<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        log.error("ConstraintViolationException {}", ex.getMessage());
        return map;
    }

    @ExceptionHandler(value = ConflictException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflictException(ConflictException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        log.error("ConflictException {}", exception.getMessage());
        return map;
    }
}
