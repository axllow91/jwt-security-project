package com.mrn.jwt_security_project.exception.domain;

public class UsernameExistsException extends Exception {
    public UsernameExistsException(String message) {
        super(message);
    }
}
