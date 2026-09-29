package com.restaurant.mapper;

import com.restaurant.model.domain.Reservation;
import com.restaurant.persistence.entity.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationEntityMapper {

    @Mapping(target = "id", ignore = true)
    ReservationEntity toEntity(Reservation reservation);

    @Mapping(target = "id", expression = "java(entity.getId() == null ? null : entity.getId().toString())")
    Reservation toDomain(ReservationEntity entity);
}