package com.larry.spring.exception;

public enum ErrorCode {
    USER_NOT_FOUND(404, "User not found"),
    USER_ALREADY_EXISTS(409, "User already exists"),
    INVALID_REQUEST(400, "Invalid request"),

    NAME_OR_PASSWORD_NOT_MATCH(400, "Name or password not match"),

    UNCATEGORIZED_EXCEPTION(500, "Uncategorized exception");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
