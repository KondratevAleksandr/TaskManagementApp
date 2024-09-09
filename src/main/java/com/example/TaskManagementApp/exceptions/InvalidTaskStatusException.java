package com.example.TaskManagementApp.exceptions;

public class InvalidTaskStatusException extends RuntimeException {
    public InvalidTaskStatusException(String message) {
        super(message);
    }
}
