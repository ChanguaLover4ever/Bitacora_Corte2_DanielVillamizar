package com.restaurant.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record VehicleEntryRequestDTO(
        @NotBlank
        String plate
) {
}