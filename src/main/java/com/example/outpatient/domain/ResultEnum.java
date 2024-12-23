package com.example.outpatient.domain;


public enum ResultEnum {
    SUCCESS(200, "Successful"),
    BAD_REQUEST(400, "Request failed"),
    UN_AUTHENTICATION(401,"Authentication failed"),
    FORBIDDEN(403, "Access denied"),
    NOT_FOUND(404, "Request not found"),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),
    FAIL(500, "System error"),
    NOT_IMPLEMENTED(501, "Not implemented yet"),
    BAD_GATEWAY(502, "The system is busy"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    ;
    private Integer code;
    private String message;

    ResultEnum() {
    }

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
