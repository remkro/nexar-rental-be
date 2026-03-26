package com.nexar.infrastructure.exception;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message) {
        super("ENTITY_NOT_FOUND", message);
    }
}

