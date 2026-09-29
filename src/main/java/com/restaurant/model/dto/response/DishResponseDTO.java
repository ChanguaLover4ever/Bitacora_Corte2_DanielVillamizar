package com.restaurant.model.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record DishResponseDTO(
        String id,
        String name,
        String category,
        BigDecimal price,
        boolean available,
        List<String> toppings
) {
}