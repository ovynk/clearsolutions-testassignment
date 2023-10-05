package com.clearsolutions.oleksiytest.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

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

    private ErrorResponse getErrorResponse(Exception exception, HttpServletRequest request, HttpStatus httpStatus) {
        return new ErrorResponse(
                httpStatus.value(),
                exception.getClass().getSimpleName(),
                exception.getLocalizedMessage(),
                request.getRequestURI()
        );
    }

}