package com.restaurant.model.dto.response;

import java.util.List;

public record OrderResponseDTO(
        String id,
        String tableId,
        String notes,
        List<String> dishIds,
        String status
) {
}