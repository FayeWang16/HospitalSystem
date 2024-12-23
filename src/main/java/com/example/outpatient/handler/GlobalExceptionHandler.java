package com.example.outpatient.handler;

import com.example.outpatient.domain.ApiResult;
import com.example.outpatient.domain.ResultEnum;
import com.example.outpatient.exception.CustomerException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Unified parameter validation failure exception handling
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handler(MethodArgumentNotValidException e) {
        String error = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(","));
        return ApiResult.result(ResultEnum.BAD_REQUEST, error, null);
    }

    /**
     * Handle BindException, which occurs when binding errors are found
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResult handleBindException(BindException e) {
        StringBuilder error = new StringBuilder();
        for (ObjectError objectError : e.getAllErrors()) {
            // Separate multiple errors with a semicolon
            if (error.length() > 0) {
                error.append(";");
            }
            // Append content to the error message
            error.append(objectError.getDefaultMessage());
        }
        return ApiResult.result(ResultEnum.BAD_REQUEST, error.toString(), null);
    }

    /**
     * Handle ConstraintViolationException, which occurs when validation constraints are violated
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ApiResult<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        Set<String> collect = set.stream().map(item -> item.getMessage()).collect(Collectors.toSet());
        return ApiResult.result(ResultEnum.BAD_REQUEST, String.join(",", collect), null);
    }

    /**
     * Handle general exceptions such as IllegalArgumentException and CustomerException
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ IllegalArgumentException.class, CustomerException.class })
    public ApiResult handler(Exception e){
        e.printStackTrace();
        return ApiResult.fail(e.getMessage());
    }

}