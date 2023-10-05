package com.clearsolutions.oleksiytest.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleEntityNotFoundException
            (EntityNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = getErrorResponse(ex, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public final ResponseEntity<ErrorResponse> handleIllegalArgumentException
            (Exception ex, HttpServletRequest request) {
        ErrorResponse error = getErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidAgeException.class)
    public final ResponseEntity<ErrorResponse> handleValidAgeException
            (ValidAgeException ex, HttpServletRequest request) {
        ErrorResponse error = getErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ErrorResponse> handleRuntimeException
            (RuntimeException ex, HttpServletRequest request) {
        ErrorResponse error = getErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getSimpleName(),
                "Argument failed validation",
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse getErrorResponse(Exception exception, HttpServletRequest request, HttpStatus httpStatus) {
        return new ErrorResponse(
                httpStatus.value(),
                exception.getClass().getSimpleName(),
                exception.getLocalizedMessage(),
                request.getRequestURI()
        );
    }

}