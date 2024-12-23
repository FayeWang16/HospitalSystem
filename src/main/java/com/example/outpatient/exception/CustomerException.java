package com.example.outpatient.exception;


public class CustomerException extends RuntimeException {
    private Integer code;

    private String message;

    public CustomerException(String message) {
        this.message = message;
        this.code = 500;
    }

    public CustomerException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public CustomerException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public CustomerException setMessage(String message) {
        this.message = message;
        return this;
    }
}
