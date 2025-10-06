package com.larry.spring.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.larry.spring.dto.request.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        System.err.println(e);
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<String>> handleAppException(AppException e) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(e.getErrorCode().getCode());
        response.setMessage(e.getErrorCode().getMessage());

        return ResponseEntity.status(e.getErrorCode().getStatusCode()).body(response);
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class)
    ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AuthorizationDeniedException e) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(ErrorCode.UNAUTHORIZED.getCode());
        response.setMessage(ErrorCode.UNAUTHORIZED.getMessage());

        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getStatusCode()).body(response);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    ResponseEntity<ApiResponse<String>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(ErrorCode.UNSUPPORTED_MEDIA_TYPE.getCode());
        response.setMessage(ErrorCode.UNSUPPORTED_MEDIA_TYPE.getMessage());

        return ResponseEntity.status(ErrorCode.UNSUPPORTED_MEDIA_TYPE.getStatusCode()).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumKey = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException ex) {

        }

        ApiResponse<String> response = new ApiResponse<>();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
