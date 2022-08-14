package com.project.springsecurity.exceptions;

public class UserNotFoundOnDatabaseException extends RuntimeException {
    public UserNotFoundOnDatabaseException(String message) {
        super(message);
    }
}
