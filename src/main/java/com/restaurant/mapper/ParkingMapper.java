package com.restaurant.mapper;

import com.restaurant.model.domain.VehicleRegistration;
import com.restaurant.model.dto.request.VehicleEntryRequestDTO;
import com.restaurant.model.dto.response.VehicleResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParkingMapper {

    VehicleRegistration toDomain(VehicleEntryRequestDTO request);

    VehicleResponseDTO toResponse(VehicleRegistration registration);

    List<VehicleResponseDTO> toResponseList(List<VehicleRegistration> registrations);
}