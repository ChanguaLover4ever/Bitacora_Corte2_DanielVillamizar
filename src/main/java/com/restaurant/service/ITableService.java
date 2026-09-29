package com.restaurant.service;

import com.restaurant.model.domain.Table;
import com.restaurant.model.domain.TableState;
import com.restaurant.model.dto.request.TableRequestDTO;

import java.util.List;

public interface ITableService {

    Table create(TableRequestDTO request);

    List<Table> findAll();

    Table findById(String id);

    Table changeState(String id, TableState state);
}