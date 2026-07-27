package pt.com.bank.banking_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import pt.com.bank.banking_api.exception.conflicts.ConflictException;
import pt.com.bank.banking_api.exception.dto.ErrorResponse;
import pt.com.bank.banking_api.exception.dto.ValidationErrorResponse;
import pt.com.bank.banking_api.exception.resources.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleDocumentTypeNotFound(
                        ResourceNotFoundException ex,
                        HttpServletRequest request) {

                return buildResponse(
                                HttpStatus.NOT_FOUND,
                                ex.getMessage(),
                                request.getRequestURI());
        }

        @ExceptionHandler(ConflictException.class)
        public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(
                        ConflictException ex,
                        HttpServletRequest request) {

                return buildResponse(
                                HttpStatus.CONFLICT,
                                ex.getMessage(),
                                request.getRequestURI());
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
                        DataIntegrityViolationException ex,
                        HttpServletRequest request) {

                return buildResponse(
                                HttpStatus.CONFLICT,
                                "The operation could not be completed because the data conflicts with existing records.",
                                request.getRequestURI());
        }

        private ResponseEntity<ErrorResponse> buildResponse(
                        HttpStatus status,
                        String message,
                        String path) {

                ErrorResponse response = new ErrorResponse(
                                LocalDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                message,
                                path);

                return ResponseEntity.status(status).body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationErrorResponse> handleValidationException(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                Map<String, String> errors = new LinkedHashMap<>();

                ex.getBindingResult()
                                .getFieldErrors()
                                .forEach(error -> errors.put(
                                                error.getField(),
                                                error.getDefaultMessage()));

                ValidationErrorResponse response = new ValidationErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                "Validation failed.",
                                request.getRequestURI(),
                                errors);

                return ResponseEntity.badRequest().body(response);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
                        HttpMessageNotReadableException ex,
                        HttpServletRequest request) {

                String message = "Invalid request body.";

                if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {

                        if (invalidFormatException.getTargetType().isEnum()) {

                                String field = invalidFormatException.getPath()
                                                .stream()
                                                .findFirst()
                                                .map(reference -> reference.getFieldName())
                                                .orElse("field");

                                String acceptedValues = Arrays
                                                .stream(invalidFormatException.getTargetType().getEnumConstants())
                                                .map(Object::toString)
                                                .collect(Collectors.joining(", "));

                                message = String.format(
                                                "Invalid value for '%s'. Accepted values are: %s.",
                                                field,
                                                acceptedValues);
                        }
                }

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                message,
                                request.getRequestURI());

                return ResponseEntity.badRequest().body(error);
        }
}
