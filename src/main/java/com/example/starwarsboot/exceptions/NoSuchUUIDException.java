package com.example.starwarsboot.exceptions;

public class NoSuchUUIDException extends BaseException {
    private static final String EXCEPTION = "Unfortunately you have inserted wrong or non-existent uuid";

    public NoSuchUUIDException() {
        super(EXCEPTION);
    }
}
