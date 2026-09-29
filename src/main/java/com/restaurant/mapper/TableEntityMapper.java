package com.restaurant.mapper;

import com.restaurant.model.domain.Table;
import com.restaurant.persistence.entity.TableEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TableEntityMapper {

    TableEntity toEntity(Table table);

    Table toDomain(TableEntity entity);
}