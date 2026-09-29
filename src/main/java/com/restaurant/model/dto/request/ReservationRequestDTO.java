package com.restaurant.model.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationRequestDTO(
        @NotNull
        @NotBlank
        String tableId,

        @NotNull
        @NotBlank
        String customerName,

        @NotNull
        @Min(1)
        Integer partySize,

        @NotNull
        @Future
        LocalDateTime reservationDateTime
) {
}