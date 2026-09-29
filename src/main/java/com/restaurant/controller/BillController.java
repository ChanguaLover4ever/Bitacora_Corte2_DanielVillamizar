package com.restaurant.controller;

import com.restaurant.mapper.BillMapper;
import com.restaurant.model.domain.Bill;
import com.restaurant.model.dto.request.BillRequestDTO;
import com.restaurant.model.dto.response.BillResponseDTO;
import com.restaurant.service.IBillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bills")
public class BillController {

    private final IBillService billService;
    private final BillMapper billMapper;

    public BillController(IBillService billService, BillMapper billMapper) {
        this.billService = billService;
        this.billMapper = billMapper;
    }

    @PostMapping
    public ResponseEntity<BillResponseDTO> create(@Valid @RequestBody BillRequestDTO request) {
        Bill bill = billService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(billMapper.toResponse(bill));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<BillResponseDTO> close(@PathVariable String id) {
        return ResponseEntity.ok(billMapper.toResponse(billService.close(id)));
    }
}