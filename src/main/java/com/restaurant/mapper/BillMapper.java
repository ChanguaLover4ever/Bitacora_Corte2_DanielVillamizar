package com.restaurant.mapper;

import com.restaurant.model.domain.Bill;
import com.restaurant.model.dto.request.BillRequestDTO;
import com.restaurant.model.dto.response.BillResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BillMapper {

    Bill toDomain(BillRequestDTO request);

    BillResponseDTO toResponse(Bill bill);

    List<BillResponseDTO> toResponseList(List<Bill> bills);
}