package com.nttdata.core.order.domain.exception;

public class OrderAlreadyExistsException extends RuntimeException {
    public OrderAlreadyExistsException(String message) {
        super(message);
    }

    public OrderAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
