package com.restaurant.model.dto.response;

import java.time.LocalDateTime;

public record ReservationResponseDTO(
        String id,
        String tableId,
        String customerName,
        int partySize,
        LocalDateTime reservationDateTime
) {
}