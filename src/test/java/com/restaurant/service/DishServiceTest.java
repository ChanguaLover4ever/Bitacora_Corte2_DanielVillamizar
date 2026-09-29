package com.restaurant.service;

import com.restaurant.exception.DishNotFoundException;
import com.restaurant.exception.WokToppingsLimitExceededException;
import com.restaurant.mapper.DishMapper;
import com.restaurant.model.domain.Dish;
import com.restaurant.model.dto.request.DishRequestDTO;
import com.restaurant.service.impl.DishServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {

    @Mock
    private DishMapper mapper;

    @InjectMocks
    private DishServiceImpl dishService;

    @Test
    void create_withFourToppings_returnsCreatedDishAndMapsRequest() {
        // Given
        DishRequestDTO request = new DishRequestDTO(
                "Wok de vegetales",
                "Wok",
                new BigDecimal("12.50"),
                List.of("zanahoria", "brócoli", "cebolla", "pimentón")
        );
        Dish mappedDish = new Dish();
        mappedDish.setName(request.name());
        mappedDish.setToppings(request.toppings());
        when(mapper.toDomain(request)).thenReturn(mappedDish);

        // When
        Dish createdDish = dishService.create(request);

        // Then
        assertNotNull(createdDish);
        verify(mapper, times(1)).toDomain(request);
    }

    @Test
    void create_withSevenToppings_throwsLimitExceptionWithoutMapping() {
        // Given
        DishRequestDTO request = new DishRequestDTO(
                "Wok de vegetales",
                "Wok",
                new BigDecimal("12.50"),
                List.of("zanahoria", "brócoli", "cebolla", "pimentón", "ajo", "jengibre", "sésamo")
        );

        // When / Then
        assertThrows(WokToppingsLimitExceededException.class, () -> dishService.create(request));
        verifyNoInteractions(mapper);
    }

    @Test
    void findById_withUnknownId_throwsDishNotFoundException() {
        // Given
        String unknownId = "missing-dish";

        // When / Then
        assertThrows(DishNotFoundException.class, () -> dishService.findById(unknownId));
    }
}