package com.restaurant.service.impl;

import com.restaurant.exception.ParkingFullException;
import com.restaurant.exception.VehicleAlreadyActiveException;
import com.restaurant.exception.VehicleNotFoundException;
import com.restaurant.mapper.VehicleRegistrationEntityMapper;
import com.restaurant.mapper.ParkingMapper;
import com.restaurant.model.domain.VehicleRegistration;
import com.restaurant.model.dto.request.VehicleEntryRequestDTO;
import com.restaurant.persistence.entity.VehicleRegistrationEntity;
import com.restaurant.repository.VehicleRegistrationRepository;
import com.restaurant.service.IParkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ParkingServiceImpl implements IParkingService {

    private static final int MAX_CAPACITY = 20;
    private static final BigDecimal CHARGE_PER_MINUTE = BigDecimal.valueOf(100);

    private final ParkingMapper parkingMapper;
    private final VehicleRegistrationRepository registrationRepository;
    private final VehicleRegistrationEntityMapper entityMapper;

    public ParkingServiceImpl(ParkingMapper parkingMapper, VehicleRegistrationRepository registrationRepository,
                              VehicleRegistrationEntityMapper entityMapper) {
        this.parkingMapper = parkingMapper;
        this.registrationRepository = registrationRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public synchronized VehicleRegistration registerEntry(VehicleEntryRequestDTO request) {
        if (registrationRepository.existsByPlateIgnoreCaseAndActiveTrue(request.plate())) {
            throw new VehicleAlreadyActiveException(request.plate());
        }

        long activeVehicles = registrationRepository.countByActiveTrue();
        if (activeVehicles >= MAX_CAPACITY) {
            throw new ParkingFullException(MAX_CAPACITY);
        }

        VehicleRegistration registration = parkingMapper.toDomain(request);
        registration.setPlate(request.plate().trim().toUpperCase());
        registration.setEntryTime(LocalDateTime.now());
        registration.setExitTime(null);
        registration.setTotalCharge(BigDecimal.ZERO);
        registration.setActive(true);
        VehicleRegistrationEntity savedEntity = registrationRepository.save(entityMapper.toEntity(registration));
        VehicleRegistration savedRegistration = entityMapper.toDomain(savedEntity);
        log.info("Vehicle {} entered the parking lot", savedRegistration.getPlate());
        return savedRegistration;
    }

    @Override
    public synchronized VehicleRegistration registerExit(String plate) {
        VehicleRegistration registration = registrationRepository.findFirstByPlateIgnoreCaseAndActiveTrue(plate)
            .map(entityMapper::toDomain)
                .orElseThrow(() -> new VehicleNotFoundException(plate));

        LocalDateTime exitTime = LocalDateTime.now();
        Duration duration = Duration.between(registration.getEntryTime(), exitTime);
        long minutes = duration.toMinutes();
        if (!duration.minusMinutes(minutes).isZero()) {
            minutes++;
        }

        registration.setExitTime(exitTime);
        registration.setTotalCharge(CHARGE_PER_MINUTE.multiply(BigDecimal.valueOf(minutes)));
        registration.setActive(false);
        VehicleRegistrationEntity savedEntity = registrationRepository.save(entityMapper.toEntity(registration));
        VehicleRegistration savedRegistration = entityMapper.toDomain(savedEntity);
        log.info("Vehicle {} exited after {} billable minutes with charge {}",
            savedRegistration.getPlate(), minutes, savedRegistration.getTotalCharge());
        return savedRegistration;
    }

    @Override
    public synchronized List<VehicleRegistration> findActive() {
        return registrationRepository.findByActiveTrue().stream()
            .map(entityMapper::toDomain)
                .toList();
    }
}