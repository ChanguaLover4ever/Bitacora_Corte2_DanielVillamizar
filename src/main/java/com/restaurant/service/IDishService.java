package com.restaurant.service;

import com.restaurant.model.domain.Dish;
import com.restaurant.model.dto.request.DishRequestDTO;

import java.util.List;

public interface IDishService {

    Dish create(DishRequestDTO request);

    List<Dish> findAll();

    List<Dish> findAvailable();

    Dish findById(String id);

    Dish changeAvailability(String id, boolean available);
}