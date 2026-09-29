package com.restaurant.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BillRequestDTO(
        @NotBlank
        String tableId
) {
}