package com.example.starwarsboot.exceptions;

public class QuarryingDatabaseException extends BaseException {
    private static final String EXCEPTION = "quarrying database exception occurred : ";

    public QuarryingDatabaseException(String message) {
        super(EXCEPTION + message);
    }
}
