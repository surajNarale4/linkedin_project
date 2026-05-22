package com.linkedIn.postsService.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFound(ResourceNotFoundException resourceNotFoundException){
        log.info("resource not found exception ");
        ApiError apiError =new ApiError(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> badRequestException(BadRequestException bad){

        ApiError apiError =new ApiError(bad.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(apiError,HttpStatus.NOT_ACCEPTABLE);


    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> resourceNotFound(BadRequestException badRequestException){
        ApiError apiError =new ApiError(badRequestException.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> resourceNotFound(RuntimeException runtimeException){
        ApiError apiError =new ApiError(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
