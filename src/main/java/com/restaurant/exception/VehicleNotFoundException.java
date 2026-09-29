package com.restaurant.exception;

public class VehicleNotFoundException extends RuntimeException {

    public VehicleNotFoundException(String plate) {
        super("No active parking registration found for vehicle: " + plate);
    }
}