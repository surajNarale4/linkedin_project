package com.linkedIn.postsService.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;

@Setter
@Getter
public class ApiError {

    private LocalDateTime localDateTime;
    private String error;
    private HttpStatus statusCode;

    public ApiError(){
        this.localDateTime=LocalDateTime.now();
    }

    public ApiError(String error, HttpStatus statusCode){
        this();
        this.error=error;
        this.statusCode=statusCode;
    }
}
