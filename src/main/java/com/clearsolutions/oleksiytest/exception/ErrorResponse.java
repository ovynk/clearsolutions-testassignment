package com.clearsolutions.oleksiytest.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String exception;
    private String details;
    private String path;

    public ErrorResponse(int status, String exception, String details, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.exception = exception;
        this.details = details;
        this.path = path;
    }

}