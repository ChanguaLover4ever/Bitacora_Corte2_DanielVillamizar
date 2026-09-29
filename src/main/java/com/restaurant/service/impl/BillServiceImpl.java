package com.restaurant.service.impl;

import com.restaurant.exception.ActiveBillExistsException;
import com.restaurant.exception.BillNotFoundException;
import com.restaurant.mapper.BillEntityMapper;
import com.restaurant.mapper.BillMapper;
import com.restaurant.model.domain.Bill;
import com.restaurant.model.domain.Order;
import com.restaurant.model.domain.TableState;
import com.restaurant.model.dto.request.BillRequestDTO;
import com.restaurant.persistence.entity.BillEntity;
import com.restaurant.repository.BillRepository;
import com.restaurant.service.IBillService;
import com.restaurant.service.IOrderService;
import com.restaurant.service.ITableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BillServiceImpl implements IBillService {

    private static final String OPEN = "OPEN";
    private static final String CLOSED = "CLOSED";

    private final ITableService tableService;
    private final IOrderService orderService;
    private final BillMapper billMapper;
    private final BillRepository billRepository;
    private final BillEntityMapper entityMapper;

    public BillServiceImpl(ITableService tableService, IOrderService orderService, BillMapper billMapper,
                           BillRepository billRepository, BillEntityMapper entityMapper) {
        this.tableService = tableService;
        this.orderService = orderService;
        this.billMapper = billMapper;
        this.billRepository = billRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public synchronized Bill create(BillRequestDTO request) {
        Table table = tableService.findById(request.tableId());
        if (billRepository.existsByTableIdAndStatus(Long.valueOf(table.getId()), OPEN)) {
            throw new ActiveBillExistsException(request.tableId());
        }

        Bill bill = billMapper.toDomain(request);
        bill.setOrderIds(List.of());
        bill.setTotal(BigDecimal.ZERO);
        bill.setStatus(OPEN);
        tableService.changeState(request.tableId(), TableState.TAKEN);
        BillEntity savedEntity = billRepository.save(entityMapper.toEntity(bill));
        Bill savedBill = entityMapper.toDomain(savedEntity);
        log.info("Opened bill {} for table {}", savedBill.getId(), savedBill.getTableId());
        return savedBill;
    }

    @Override
    public synchronized Bill close(String id) {
        Bill bill = findById(id);
        if (CLOSED.equals(bill.getStatus())) {
            return bill;
        }

        List<String> billedOrderIds = billRepository.findAll().stream()
                .filter(existingBill -> !existingBill.getId().equals(parseId(id)))
                .flatMap(existingBill -> Optional.ofNullable(existingBill.getOrderIds()).stream().flatMap(List::stream))
                .toList();
        List<Order> unbilledOrders = orderService.findAll().stream()
                .filter(order -> bill.getTableId().equals(order.getTableId()))
                .filter(order -> !billedOrderIds.contains(order.getId()))
                .toList();
        BigDecimal orderTotal = unbilledOrders.stream()
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        bill.setOrderIds(unbilledOrders.stream().map(Order::getId).toList());
        bill.setTotal(orderTotal);
        bill.setStatus(CLOSED);
        tableService.changeState(bill.getTableId(), TableState.AVAILABLE);
        BillEntity savedEntity = billRepository.save(entityMapper.toEntity(bill));
        log.info("Closed bill {} for table {} with total {}", bill.getId(), bill.getTableId(), orderTotal);
        return entityMapper.toDomain(savedEntity);
    }

    private Bill findById(String id) {
        return billRepository.findById(parseId(id))
                .map(entityMapper::toDomain)
                .orElseThrow(() -> new BillNotFoundException(id));
    }

    private Long parseId(String id) {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException exception) {
            throw new BillNotFoundException(id);
        }
    }
}