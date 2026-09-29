package com.restaurant.service;

import com.restaurant.model.domain.Bill;
import com.restaurant.model.dto.request.BillRequestDTO;

public interface IBillService {

    Bill create(BillRequestDTO request);

    Bill close(String id);
}