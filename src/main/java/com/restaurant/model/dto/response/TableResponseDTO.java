package com.restaurant.model.dto.response;

import com.restaurant.model.domain.TableState;

public record TableResponseDTO(
        String id,
        int number,
        int capacity,
        TableState state
) {
}