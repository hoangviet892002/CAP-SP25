package com.CP.KPCOS.shared.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter

public enum ResponseEnum {

    TOKEN_INVALID(1001, "Token is invalid or expired", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1002, "Invalid credentials", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD_OR_USERNAME(1003, "Wrong password or username", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(1004, "User already exists", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1005, "Email already exists", HttpStatus.BAD_REQUEST),
    TOKEN_MISSING(1006, "Token is missing", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TOKEN(1007, "Invalid token", HttpStatus.INTERNAL_SERVER_ERROR),



    ;


    private final int code;

    private final String message;

    private final HttpStatusCode statusCode;

    ResponseEnum(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
