package com.e_garden.api.exception;

/**
 * Le type Object not found exception.
 */
public class ObjectNotFoundException extends RuntimeException {
    /**
     * Instancies un nouveau Object not found exception.
     *
     * @param message le message
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}