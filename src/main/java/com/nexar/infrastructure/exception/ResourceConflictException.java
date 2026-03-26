package com.nexar.infrastructure.exception;

public class ResourceConflictException extends BusinessException {
    public ResourceConflictException(String message) {
        super("CONFLICT", message);
    }
}
