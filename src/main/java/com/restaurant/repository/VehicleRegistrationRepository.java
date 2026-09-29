package com.restaurant.repository;

import com.restaurant.persistence.entity.VehicleRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRegistrationRepository extends JpaRepository<VehicleRegistrationEntity, Long> {

    boolean existsByPlateIgnoreCaseAndActiveTrue(String plate);

    long countByActiveTrue();

    Optional<VehicleRegistrationEntity> findFirstByPlateIgnoreCaseAndActiveTrue(String plate);

    List<VehicleRegistrationEntity> findByActiveTrue();
}