package com.restaurant.controller;

import com.restaurant.mapper.OrderMapper;
import com.restaurant.model.domain.Order;
import com.restaurant.model.dto.request.OrderRequestDTO;
import com.restaurant.model.dto.response.OrderResponseDTO;
import com.restaurant.service.IOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final IOrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(IOrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderRequestDTO request) {
        Order createdOrder = orderService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.toResponse(createdOrder));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(orderMapper.toResponseList(orders));
    }
}