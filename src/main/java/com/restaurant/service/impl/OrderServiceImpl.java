package com.restaurant.service.impl;

import com.restaurant.mapper.OrderMapper;
import com.restaurant.mapper.OrderEntityMapper;
import com.restaurant.model.domain.Dish;
import com.restaurant.model.domain.Order;
import com.restaurant.model.dto.request.OrderRequestDTO;
import com.restaurant.persistence.entity.OrderEntity;
import com.restaurant.repository.OrderRepository;
import com.restaurant.service.IDishService;
import com.restaurant.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    private static final int MAX_ORDERS_PREPARING = 4;
    private static final String PREPARING = "PREPARING";
    private static final String RECEIVED = "RECEIVED";

    private final IDishService dishService;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderEntityMapper entityMapper;

    public OrderServiceImpl(IDishService dishService, OrderMapper orderMapper,
                            OrderRepository orderRepository, OrderEntityMapper entityMapper) {
        this.dishService = dishService;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public synchronized Order create(OrderRequestDTO request) {
        BigDecimal total = request.dishIds().stream()
                .map(dishService::findById)
                .map(Dish::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = orderMapper.toDomain(request);
        order.setTotal(total);

    long preparingOrders = orderRepository.countByStatus(PREPARING);
        if (preparingOrders < MAX_ORDERS_PREPARING) {
            order.setStatus(PREPARING);
        } else {
            order.setStatus(RECEIVED);
        }

    OrderEntity savedEntity = orderRepository.save(entityMapper.toEntity(order));
    Order savedOrder = entityMapper.toDomain(savedEntity);
        log.info("Order {} assigned status {} ({} orders already preparing)",
        savedOrder.getId(), savedOrder.getStatus(), preparingOrders);
    return savedOrder;
    }

    @Override
    public List<Order> findAll() {
    return orderRepository.findAll().stream()
        .map(entityMapper::toDomain)
        .toList();
    }
}