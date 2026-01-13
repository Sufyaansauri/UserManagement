package com.UserManagement.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Response> handleNoHandlerFound() {

        Response response = new Response();
        response.setResponse(UMSResponse.BAD_REQUEST);
        response.setMessage("Id is required");

        return ResponseEntity.badRequest().body(response);
    }
}
