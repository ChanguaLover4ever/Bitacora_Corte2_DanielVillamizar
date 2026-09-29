package com.restaurant.controller;

import com.restaurant.mapper.ReservationMapper;
import com.restaurant.model.domain.Reservation;
import com.restaurant.model.dto.request.ReservationRequestDTO;
import com.restaurant.model.dto.response.ReservationResponseDTO;
import com.restaurant.service.IReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final IReservationService reservationService;
    private final ReservationMapper reservationMapper;

    public ReservationController(IReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(@Valid @RequestBody ReservationRequestDTO request) {
        Reservation reservation = reservationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationMapper.toResponse(reservation));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> findAll() {
        List<Reservation> reservations = reservationService.findAll();
        return ResponseEntity.ok(reservationMapper.toResponseList(reservations));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}