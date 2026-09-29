package com.restaurant.service.impl;

import com.restaurant.exception.DishNotFoundException;
import com.restaurant.exception.WokToppingsLimitExceededException;
import com.restaurant.mapper.DishMapper;
import com.restaurant.mapper.DishEntityMapper;
import com.restaurant.model.domain.Dish;
import com.restaurant.model.dto.request.DishRequestDTO;
import com.restaurant.persistence.entity.DishEntity;
import com.restaurant.repository.DishRepository;
import com.restaurant.service.IDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements IDishService {

    private static final int MAX_WOK_TOPPINGS = 6;

    private final DishMapper dishMapper;
    private final DishRepository dishRepository;
    private final DishEntityMapper entityMapper;

    public DishServiceImpl(DishMapper dishMapper, DishRepository dishRepository, DishEntityMapper entityMapper) {
        this.dishMapper = dishMapper;
        this.dishRepository = dishRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Dish create(DishRequestDTO request) {
        if (request.toppings() != null && request.toppings().size() > MAX_WOK_TOPPINGS) {
            log.warn("Dish creation rejected: {} toppings exceed the maximum of {}",
                    request.toppings().size(), MAX_WOK_TOPPINGS);
            throw new WokToppingsLimitExceededException(MAX_WOK_TOPPINGS);
        }

        Dish dish = dishMapper.toDomain(request);
        dish.setAvailable(true);
        DishEntity savedEntity = dishRepository.save(entityMapper.toEntity(dish));
        Dish savedDish = entityMapper.toDomain(savedEntity);
        log.info("Dish created successfully with id {}", savedDish.getId());
        return savedDish;
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll().stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Dish> findAvailable() {
        return dishRepository.findAll().stream()
                .map(entityMapper::toDomain)
                .filter(Dish::isAvailable)
                .toList();
    }

    @Override
    public Dish findById(String id) {
        return dishRepository.findById(parseId(id))
                .map(entityMapper::toDomain)
                .orElseThrow(() -> new DishNotFoundException(id));
    }

    @Override
    public Dish changeAvailability(String id, boolean available) {
        Dish dish = findById(id);
        dish.setAvailable(available);
        return entityMapper.toDomain(dishRepository.save(entityMapper.toEntity(dish)));
    }

    private Long parseId(String id) {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException exception) {
            throw new DishNotFoundException(id);
        }
    }
}