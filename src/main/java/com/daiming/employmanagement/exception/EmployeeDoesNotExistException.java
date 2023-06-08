package com.daiming.employmanagement.exception;

public class EmployeeDoesNotExistException extends RuntimeException{
    public EmployeeDoesNotExistException(String message) {
        super(message);
    }
}
