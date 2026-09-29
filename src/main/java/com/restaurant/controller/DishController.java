package com.restaurant.controller;

import com.restaurant.mapper.DishMapper;
import com.restaurant.model.dto.request.DishRequestDTO;
import com.restaurant.model.dto.response.DishResponseDTO;
import com.restaurant.service.IDishService;
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
@RequestMapping("/api/v1/dishes")
public class DishController {

    private final IDishService dishService;
    private final DishMapper dishMapper;

    public DishController(IDishService dishService, DishMapper dishMapper) {
        this.dishService = dishService;
        this.dishMapper = dishMapper;
    }

    @PostMapping
    public ResponseEntity<DishResponseDTO> create(@Valid @RequestBody DishRequestDTO request) {
        var createdDish = dishService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dishMapper.toResponse(createdDish));
    }

    @GetMapping
    public ResponseEntity<List<DishResponseDTO>> findAll() {
        return ResponseEntity.ok(dishMapper.toResponseList(dishService.findAll()));
    }
}