package com.paulomlr.ecommerceSystem.services.exceptions;

public class UniqueConstraintViolationException extends RuntimeException {
    public UniqueConstraintViolationException(String message) {
        super(message);
    }
}
