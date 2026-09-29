package com.restaurant.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VehicleResponseDTO(
        String plate,
        LocalDateTime entryTime,
        LocalDateTime exitTime,
        BigDecimal totalCharge,
        boolean active
) {
}