package com.busticketpro.service;

import com.busticketpro.entity.Location;
import com.busticketpro.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> findAll() {
        return locationRepository.findAll();
    }
}