package com.library.management.config;

import com.library.management.exceptions.BookOperationFailed;
import com.library.management.exceptions.CustomBaseException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.ott.InvalidOneTimeTokenException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle validation errors
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode  status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());

        // Get all field errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    // Handle security exceptions
    @ExceptionHandler({ AuthenticationException.class, AccessDeniedException.class , UsernameNotFoundException.class , InvalidOneTimeTokenException.class})
    public ResponseEntity<Object> handleSecurityException(RuntimeException ex, WebRequest request) {
        HttpStatus status = ex instanceof AuthenticationException ?
                HttpStatus.UNAUTHORIZED : HttpStatus.FORBIDDEN;
        return buildErrorResponse(ex, status, request);
    }

    @ExceptionHandler({ BookOperationFailed.class })
    public ResponseEntity<Object> hanldeCustomExceptions(CustomBaseException ex, WebRequest request) {
        HttpStatus status = ex.getStatus() == null ?
                HttpStatus.INTERNAL_SERVER_ERROR : ex.getStatus();
        return buildErrorResponse(ex.getMessage(), status, request);
    }

    // Handle database exceptions
    @ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class })
    public ResponseEntity<Object> handleDataException(RuntimeException ex, WebRequest request) {
        String message = "Data processing error";
        if (ex instanceof ConstraintViolationException) {
            message = ((ConstraintViolationException) ex).getConstraintViolations()
                    .stream()
                    .map(cv -> cv.getMessage())
                    .collect(Collectors.joining(", "));
        }
        return buildErrorResponse(message, HttpStatus.CONFLICT, request);
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        // Set appropriate status for common exceptions
        if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof NoSuchElementException) {
            status = HttpStatus.NOT_FOUND;
        }

        return buildErrorResponse(ex.getMessage(), status, request);
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, status);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), status, request);
    }

}