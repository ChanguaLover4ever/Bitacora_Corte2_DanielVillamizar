package com.restaurant.mapper;

import com.restaurant.model.domain.VehicleRegistration;
import com.restaurant.persistence.entity.VehicleRegistrationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleRegistrationEntityMapper {

    VehicleRegistrationEntity toEntity(VehicleRegistration registration);

    VehicleRegistration toDomain(VehicleRegistrationEntity entity);
}