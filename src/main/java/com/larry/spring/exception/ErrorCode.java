package com.larry.spring.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public enum ErrorCode {
    USER_NOT_FOUND(404, "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(409, "User already exists", HttpStatus.CONFLICT),
    INVALID_REQUEST(400, "Invalid request", HttpStatus.BAD_REQUEST),

    NAME_OR_PASSWORD_NOT_MATCH(400, "Name or password not match", HttpStatus.BAD_REQUEST),

    UNCATEGORIZED_EXCEPTION(500, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR);

    int code;
    HttpStatus statusCode;
    String message;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
