package com.nexar.infrastructure.exception;

public enum ExceptionCategory {
    BUSINESS("business"),
    TECHNICAL("technical");

    private final String shortName;

    ExceptionCategory(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}


