package com.restaurant.service.impl;

import com.restaurant.exception.ReservationConflictException;
import com.restaurant.exception.ReservationNotFoundException;
import com.restaurant.mapper.ReservationEntityMapper;
import com.restaurant.mapper.ReservationMapper;
import com.restaurant.model.domain.Reservation;
import com.restaurant.model.dto.request.ReservationRequestDTO;
import com.restaurant.persistence.entity.ReservationEntity;
import com.restaurant.repository.ReservationRepository;
import com.restaurant.service.IReservationService;
import com.restaurant.service.ITableService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReservationServiceImpl implements IReservationService {

    private static final Duration RESERVATION_DURATION = Duration.ofHours(1);

    private final ITableService tableService;
    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;
    private final ReservationEntityMapper entityMapper;

    public ReservationServiceImpl(ITableService tableService, ReservationMapper reservationMapper,
                                  ReservationRepository reservationRepository,
                                  ReservationEntityMapper entityMapper) {
        this.tableService = tableService;
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public synchronized Reservation create(ReservationRequestDTO request) {
        com.restaurant.model.domain.Table table = tableService.findById(request.tableId());

        LocalDateTime requestedStart = request.reservationDateTime();
        LocalDateTime requestedEnd = requestedStart.plus(RESERVATION_DURATION);
        boolean hasConflict = reservationRepository.findAll().stream()
            .map(entityMapper::toDomain)
                .anyMatch(reservation -> request.tableId().equals(reservation.getTableId())
                && requestedStart.isBefore(reservation.getReservationDateTime().plus(RESERVATION_DURATION))
                && reservation.getReservationDateTime().isBefore(requestedEnd));
        if (hasConflict) {
            throw new ReservationConflictException(request.tableId(), requestedStart);
        }

        Reservation reservation = reservationMapper.toDomain(request);
        reservation.setTableId(table.getId());
        ReservationEntity savedEntity = reservationRepository.save(entityMapper.toEntity(reservation));
        return entityMapper.toDomain(savedEntity);
    }

    @Override
    public synchronized List<Reservation> findAll() {
        return reservationRepository.findAll().stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public synchronized void deleteById(String id) {
        UUID reservationId = parseId(id);
        if (reservationRepository.findById(reservationId).isEmpty()) {
            throw new ReservationNotFoundException(id);
        }
        reservationRepository.deleteById(reservationId);
    }

    private UUID parseId(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException exception) {
            throw new ReservationNotFoundException(id);
        }
    }
}