package com.busticketpro.service;

import com.busticketpro.entity.Route;
import com.busticketpro.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Route getById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy tuyến"));
    }

    public void save(Route route) {
        routeRepository.save(route);
    }

    public void delete(Long id) {
        routeRepository.deleteById(id);
    }
}