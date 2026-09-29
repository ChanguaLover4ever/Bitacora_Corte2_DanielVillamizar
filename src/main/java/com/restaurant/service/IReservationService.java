package com.restaurant.service;

import com.restaurant.model.domain.Reservation;
import com.restaurant.model.dto.request.ReservationRequestDTO;

import java.util.List;

public interface IReservationService {

    Reservation create(ReservationRequestDTO request);

    List<Reservation> findAll();

    void deleteById(String id);
}