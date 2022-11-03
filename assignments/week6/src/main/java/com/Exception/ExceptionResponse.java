package com.Exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {
    private String msg;
    private LocalDateTime localDateTime;
}
