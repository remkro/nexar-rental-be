package com.nexar.infrastructure.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ExceptionCategory category;
    private final String errorCode;

    public BusinessException(String errorCode, String message) {
        super(message);
        this.category = ExceptionCategory.BUSINESS;
        this.errorCode = errorCode;
    }
}