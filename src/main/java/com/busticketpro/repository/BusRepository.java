package com.busticketpro.repository;

import com.busticketpro.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository
        extends JpaRepository<Bus, Long> {
}