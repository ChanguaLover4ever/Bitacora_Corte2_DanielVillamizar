package com.restaurant.exception;

public class ActiveBillExistsException extends RuntimeException {

    public ActiveBillExistsException(String tableId) {
        super("An open bill already exists for table: " + tableId);
    }
}