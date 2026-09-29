package com.restaurant.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO(
        @NotNull
        String tableId,

        String notes,

        @NotEmpty
        List<String> dishIds
) {
}