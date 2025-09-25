package com.larry.spring.exception;

public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
    
    private ErrorCode errorCode;
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
