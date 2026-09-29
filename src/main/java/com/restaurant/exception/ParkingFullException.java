package com.restaurant.exception;

public class ParkingFullException extends RuntimeException {

    public ParkingFullException(int capacity) {
        super("Parking is full. Maximum capacity is " + capacity + " vehicles");
    }
}