package com.example.starwarsboot.exceptions;

public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(final String fmt, final Object... args) {
        super(String.format(fmt, args));
    }
}
