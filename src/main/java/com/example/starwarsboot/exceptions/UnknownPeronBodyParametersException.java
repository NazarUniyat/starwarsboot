package com.example.starwarsboot.exceptions;

public class UnknownPeronBodyParametersException extends BaseException {

    private static final String EXCEPTION = "Sorry, but there is no information about the mass or height of one of the characters";

    public UnknownPeronBodyParametersException() {
        super(EXCEPTION);
    }
}
