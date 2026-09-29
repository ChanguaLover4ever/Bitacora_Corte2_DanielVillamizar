package com.restaurant.exception;

public class WokToppingsLimitExceededException extends RuntimeException {

    public WokToppingsLimitExceededException(int maximumToppings) {
        super("A wok dish cannot contain more than " + maximumToppings + " toppings");
    }
}