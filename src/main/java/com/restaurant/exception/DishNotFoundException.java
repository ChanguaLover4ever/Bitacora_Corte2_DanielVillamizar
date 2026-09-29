package com.restaurant.exception;

public class DishNotFoundException extends RuntimeException {

    public DishNotFoundException(String id) {
        super("Dish not found with id: " + id);
    }
}