package com.restaurant.mapper;

import com.restaurant.model.domain.Table;
import com.restaurant.model.dto.request.TableRequestDTO;
import com.restaurant.model.dto.response.TableResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TableMapper {

    Table toDomain(TableRequestDTO request);

    TableResponseDTO toResponse(Table table);

    List<TableResponseDTO> toResponseList(List<Table> tables);
}