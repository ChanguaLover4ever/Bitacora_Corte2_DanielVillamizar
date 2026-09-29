package com.restaurant.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record DishRequestDTO(
        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Size(max = 60)
        String category,

        @NotNull
        @DecimalMin("0.01")
        @Digits(integer = 8, fraction = 2)
        BigDecimal price,

        @NotNull
        List<String> toppings
) {
}