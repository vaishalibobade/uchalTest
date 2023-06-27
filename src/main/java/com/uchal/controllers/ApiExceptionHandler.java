package com.uchal.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uchal.model.ApiError;
import com.uchal.model.ApiException;

@ControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleApiException(ApiException ex) {
        ApiError error = new ApiError(ex.getMessage(), ex.getStatusCode());
        return new ResponseEntity<>(error, HttpStatus.valueOf(ex.getStatusCode()));
    }
}

