package com.restaurant.model.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record BillResponseDTO(
        String id,
        String tableId,
        List<String> orderIds,
        BigDecimal total,
        String status
) {
}