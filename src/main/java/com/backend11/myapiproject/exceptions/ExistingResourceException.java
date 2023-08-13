package com.backend11.myapiproject.exceptions;

public class ExistingResourceException extends RuntimeException{

    public static final String MESSAGE = "El recurso que se está intentando crear ya existe";

    public ExistingResourceException() {
    }

    public ExistingResourceException(String message) {
        super(message);
    }
}
