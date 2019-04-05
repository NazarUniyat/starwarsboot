package com.example.starwarsboot.controllers;

import com.example.starwarsboot.exceptions.NoSuchUUIDException;
import com.example.starwarsboot.exceptions.QuarryingSourcesException;
import com.example.starwarsboot.exceptions.UnknownPeronBodyParametersException;
import com.example.starwarsboot.wires.ExceptionResponseWire;
import com.example.starwarsboot.wires.ValidatorResponseWire;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(QuarryingSourcesException.class)
    public ResponseEntity recordNotFoundException(QuarryingSourcesException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseWire(HttpStatus.NOT_FOUND.toString(),e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidatorResponseWire> recordNotFoundException(ConstraintViolationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ValidatorResponseWire(HttpStatus.BAD_REQUEST.toString(),e.getMessage()));
    }

    @ExceptionHandler(UnknownPeronBodyParametersException.class)
    public ResponseEntity recordNotFoundException(UnknownPeronBodyParametersException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseWire(HttpStatus.BAD_REQUEST.toString(),e.getMessage()));
    }

    @ExceptionHandler(NoSuchUUIDException.class)
    public ResponseEntity recordNotFoundException(NoSuchUUIDException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseWire(HttpStatus.BAD_REQUEST.toString(),e.getMessage()));
    }
}
