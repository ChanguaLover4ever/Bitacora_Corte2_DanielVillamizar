package com.restaurant.mapper;

import com.restaurant.model.domain.Dish;
import com.restaurant.persistence.entity.DishEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DishEntityMapper {

    DishEntity toEntity(Dish dish);

    Dish toDomain(DishEntity entity);
}