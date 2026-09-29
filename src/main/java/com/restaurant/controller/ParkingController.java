package com.restaurant.controller;

import com.restaurant.mapper.ParkingMapper;
import com.restaurant.model.domain.VehicleRegistration;
import com.restaurant.model.dto.request.VehicleEntryRequestDTO;
import com.restaurant.model.dto.response.VehicleResponseDTO;
import com.restaurant.service.IParkingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final IParkingService parkingService;
    private final ParkingMapper parkingMapper;

    public ParkingController(IParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @PostMapping("/entry")
    public ResponseEntity<VehicleResponseDTO> registerEntry(@Valid @RequestBody VehicleEntryRequestDTO request) {
        VehicleRegistration registration = parkingService.registerEntry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingMapper.toResponse(registration));
    }

    @PostMapping("/exit/{plate}")
    public ResponseEntity<VehicleResponseDTO> registerExit(@PathVariable String plate) {
        return ResponseEntity.ok(parkingMapper.toResponse(parkingService.registerExit(plate)));
    }

    @GetMapping("/active")
    public ResponseEntity<List<VehicleResponseDTO>> findActive() {
        return ResponseEntity.ok(parkingMapper.toResponseList(parkingService.findActive()));
    }
}