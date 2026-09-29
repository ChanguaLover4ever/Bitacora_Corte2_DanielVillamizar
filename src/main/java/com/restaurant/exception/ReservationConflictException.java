package com.restaurant.exception;

import java.time.LocalDateTime;

public class ReservationConflictException extends RuntimeException {

    public ReservationConflictException(String tableId, LocalDateTime reservationDateTime) {
        super("A reservation already overlaps for table " + tableId + " at " + reservationDateTime);
    }
}