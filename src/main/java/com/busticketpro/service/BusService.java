package com.busticketpro.service;

import com.busticketpro.entity.Bus;
import com.busticketpro.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Bus getById(Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
    }

    public Bus save(Bus bus) {
        return busRepository.save(bus);
    }

    public void delete(Long id) {
        busRepository.deleteById(id);
    }
}