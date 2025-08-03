package com.CP.KPCOS.exceptions;

public class ApplicationExeption extends RuntimeException{
    public ApplicationExeption(String message) {
        super(message);
    }

    public ApplicationExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
