package com.mrn.jwt_security_project.domain;

import org.springframework.http.HttpStatus;

/*
* code: 200,
* httpStatus: OK
* reason: OK
* message: Here you can add your message -> "Your request was successfully"
*
* */
public class HttpResponse {
    private int httpStatusCode; // like 200, 201, 400 - file not found, url, 500 -server error
    private HttpStatus httpStatus;
    private String reason; // reason phrase like httpstatus: 200, phrase: ok
    private String message; // message that you want to return

    public HttpResponse() {
    }

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
