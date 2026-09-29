package com.restaurant.mapper;

import com.restaurant.model.domain.Reservation;
import com.restaurant.model.dto.request.ReservationRequestDTO;
import com.restaurant.model.dto.response.ReservationResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    Reservation toDomain(ReservationRequestDTO request);

    ReservationResponseDTO toResponse(Reservation reservation);

    List<ReservationResponseDTO> toResponseList(List<Reservation> reservations);
}