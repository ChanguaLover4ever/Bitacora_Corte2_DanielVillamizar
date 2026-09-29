package com.restaurant.mapper;

import com.restaurant.model.domain.Order;
import com.restaurant.persistence.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", expression = "java(order.getDishIds() == null ? java.util.List.of() : order.getDishIds().stream().map(com.restaurant.persistence.entity.OrderItemEntity::new).toList())")
    OrderEntity toEntity(Order order);

    @Mapping(target = "id", expression = "java(entity.getId() == null ? null : entity.getId().toString())")
    @Mapping(target = "dishIds", expression = "java(entity.getItems() == null ? java.util.List.of() : entity.getItems().stream().map(com.restaurant.persistence.entity.OrderItemEntity::getDishId).toList())")
    Order toDomain(OrderEntity entity);
}