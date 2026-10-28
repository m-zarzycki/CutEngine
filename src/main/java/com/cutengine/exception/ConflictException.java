package com.cutengine.exception;

public abstract class ConflictException extends RuntimeException {
    
    protected ConflictException(String message) {
        super(message);
    }
}
