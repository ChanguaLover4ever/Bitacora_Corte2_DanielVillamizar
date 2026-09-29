package com.restaurant.exception;

public class VehicleAlreadyActiveException extends RuntimeException {

    public VehicleAlreadyActiveException(String plate) {
        super("Vehicle already has an active parking registration: " + plate);
    }
}