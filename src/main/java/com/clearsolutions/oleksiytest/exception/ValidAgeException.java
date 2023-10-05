package com.clearsolutions.oleksiytest.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidAgeException extends ValidationException {

    public ValidAgeException() {}

    public ValidAgeException(String message) {
        super(message);
    }

}