package com.restaurant.controller;

import com.restaurant.mapper.TableMapper;
import com.restaurant.model.domain.Table;
import com.restaurant.model.dto.request.TableRequestDTO;
import com.restaurant.model.dto.response.TableResponseDTO;
import com.restaurant.service.ITableService;
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
@RequestMapping("/api/v1/tables")
public class TableController {

    private final ITableService tableService;
    private final TableMapper tableMapper;

    public TableController(ITableService tableService, TableMapper tableMapper) {
        this.tableService = tableService;
        this.tableMapper = tableMapper;
    }

    @PostMapping
    public ResponseEntity<TableResponseDTO> create(@Valid @RequestBody TableRequestDTO request) {
        Table table = tableService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tableMapper.toResponse(table));
    }

    @GetMapping
    public ResponseEntity<List<TableResponseDTO>> findAll() {
        return ResponseEntity.ok(tableMapper.toResponseList(tableService.findAll()));
    }
}