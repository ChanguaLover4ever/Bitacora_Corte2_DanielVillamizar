package com.restaurant.mapper;

import com.restaurant.model.domain.Dish;
import com.restaurant.model.dto.request.DishRequestDTO;
import com.restaurant.model.dto.response.DishResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DishMapper {

    Dish toDomain(DishRequestDTO request);

    DishResponseDTO toResponse(Dish dish);

    List<DishResponseDTO> toResponseList(List<Dish> dishes);
}