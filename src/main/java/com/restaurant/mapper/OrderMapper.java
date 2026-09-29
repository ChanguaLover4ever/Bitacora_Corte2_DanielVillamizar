package com.restaurant.mapper;

import com.restaurant.model.domain.Order;
import com.restaurant.model.dto.request.OrderRequestDTO;
import com.restaurant.model.dto.response.OrderResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toDomain(OrderRequestDTO request);

    OrderResponseDTO toResponse(Order order);

    List<OrderResponseDTO> toResponseList(List<Order> orders);
}