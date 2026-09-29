package com.restaurant.repository;

import com.restaurant.persistence.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<BillEntity, Long> {

    boolean existsByTableIdAndStatus(Long tableId, String status);
}