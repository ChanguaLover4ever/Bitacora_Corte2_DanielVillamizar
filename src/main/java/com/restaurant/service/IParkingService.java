package com.restaurant.service;

import com.restaurant.model.domain.VehicleRegistration;
import com.restaurant.model.dto.request.VehicleEntryRequestDTO;

import java.util.List;

public interface IParkingService {

    VehicleRegistration registerEntry(VehicleEntryRequestDTO request);

    VehicleRegistration registerExit(String plate);

    List<VehicleRegistration> findActive();
}