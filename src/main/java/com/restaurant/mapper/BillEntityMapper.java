package com.restaurant.mapper;

import com.restaurant.model.domain.Bill;
import com.restaurant.persistence.entity.BillEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillEntityMapper {

    BillEntity toEntity(Bill bill);

    Bill toDomain(BillEntity entity);
}