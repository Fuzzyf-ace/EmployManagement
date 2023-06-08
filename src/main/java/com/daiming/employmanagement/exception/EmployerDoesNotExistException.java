package com.daiming.employmanagement.exception;

public class EmployerDoesNotExistException extends RuntimeException{
    public EmployerDoesNotExistException(String message) {
        super(message);
    }

}
