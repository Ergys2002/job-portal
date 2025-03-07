package com.exh.jobseeker.exception;

import com.exh.jobseeker.model.dto.response.ErrorResponse;
import com.exh.jobseeker.model.dto.response.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ErrorResponse> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .path(getPath(request))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .path(getPath(request))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePhoneNumberAlreadyExistsException(PhoneNumberAlreadyExistsException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .path(getPath(request))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTokenNotFoundException(TokenNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .path(getPath(request))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSessionNotFoundException(SessionNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .path(getPath(request))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .path(getPath(request))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .path(getPath(request))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidUserRole.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserRole(InvalidUserRole ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .path(getPath(request))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        ValidationErrorResponse errorResponse = ValidationErrorResponse.validationBuilder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .timestamp(Instant.now())
                .path(getPath(request))
                .validationErrors(validationErrors)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String getPath(WebRequest request) {
        String path = null;
        if (request instanceof ServletWebRequest) {
            path = ((ServletWebRequest) request).getRequest().getRequestURI();
        }
        return path;
    }
}
