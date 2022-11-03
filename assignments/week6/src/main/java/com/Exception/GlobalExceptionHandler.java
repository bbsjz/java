package com.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({GoodsException.class})
    public ResponseEntity<Object> handle(GoodsException exception)
    {
        ExceptionResponse exceptionResponse=new ExceptionResponse();
        exceptionResponse.setMsg(exception.getMessage());
        exceptionResponse.setLocalDateTime(LocalDateTime.now());
        ResponseEntity<Object> responseEntity=new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }
}
