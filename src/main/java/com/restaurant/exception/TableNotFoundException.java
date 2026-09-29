package com.restaurant.exception;

public class TableNotFoundException extends RuntimeException {

    public TableNotFoundException(String id) {
        super("Table not found with id: " + id);
    }
}