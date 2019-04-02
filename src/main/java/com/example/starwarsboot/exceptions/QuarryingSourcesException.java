package com.example.starwarsboot.exceptions;

public class QuarryingSourcesException extends BaseException {
    private static final String EXCEPTION = "oops, something went wrong. : ";

    public QuarryingSourcesException(String message) {
        super(EXCEPTION + message);
    }
}
