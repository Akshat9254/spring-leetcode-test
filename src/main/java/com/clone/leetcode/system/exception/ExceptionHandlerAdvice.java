package com.clone.leetcode.system.exception;

import com.clone.leetcode.system.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new Response<>(false, HttpStatus.NOT_FOUND.value(),
                "Resource not found, see data field for more info.", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> handleException(Exception ex) {
        return new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error", ex.getMessage());
    }
}
