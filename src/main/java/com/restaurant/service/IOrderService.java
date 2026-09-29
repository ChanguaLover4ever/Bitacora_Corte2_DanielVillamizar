package com.restaurant.service;

import com.restaurant.model.domain.Order;
import com.restaurant.model.dto.request.OrderRequestDTO;

import java.util.List;

public interface IOrderService {

    Order create(OrderRequestDTO request);

    List<Order> findAll();
}