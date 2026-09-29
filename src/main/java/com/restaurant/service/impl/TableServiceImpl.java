package com.restaurant.service.impl;

import com.restaurant.exception.TableNotFoundException;
import com.restaurant.mapper.TableEntityMapper;
import com.restaurant.mapper.TableMapper;
import com.restaurant.model.domain.Table;
import com.restaurant.model.domain.TableState;
import com.restaurant.model.dto.request.TableRequestDTO;
import com.restaurant.persistence.entity.TableEntity;
import com.restaurant.repository.TableRepository;
import com.restaurant.service.ITableService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImpl implements ITableService {

    private final TableMapper tableMapper;
    private final TableRepository tableRepository;
    private final TableEntityMapper entityMapper;

    public TableServiceImpl(TableMapper tableMapper, TableRepository tableRepository, TableEntityMapper entityMapper) {
        this.tableMapper = tableMapper;
        this.tableRepository = tableRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Table create(TableRequestDTO request) {
        Table table = tableMapper.toDomain(request);
        table.setState(TableState.AVAILABLE);
        TableEntity savedEntity = tableRepository.save(entityMapper.toEntity(table));
        return entityMapper.toDomain(savedEntity);
    }

    @Override
    public List<Table> findAll() {
        return tableRepository.findAll().stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public Table findById(String id) {
        return tableRepository.findById(parseId(id))
                .map(entityMapper::toDomain)
                .orElseThrow(() -> new TableNotFoundException(id));
    }

    @Override
    public Table changeState(String id, TableState state) {
        Table table = findById(id);
        table.setState(state);
        return entityMapper.toDomain(tableRepository.save(entityMapper.toEntity(table)));
    }

    private Long parseId(String id) {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException exception) {
            throw new TableNotFoundException(id);
        }
    }
}