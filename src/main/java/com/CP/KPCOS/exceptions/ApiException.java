package com.CP.KPCOS.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiException extends RuntimeException{
    private int statusCode;
    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
    public ApiException(String message) {
        super(message);
        this.statusCode = 500; // Default status code
    }
}
