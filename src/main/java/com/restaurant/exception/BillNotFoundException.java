package com.restaurant.exception;

public class BillNotFoundException extends RuntimeException {

    public BillNotFoundException(String id) {
        super("Bill not found with id: " + id);
    }
}