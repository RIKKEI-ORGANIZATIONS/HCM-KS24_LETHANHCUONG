package com.busticketpro.service;

import com.busticketpro.entity.Seat;
import com.busticketpro.entity.Trip;
import com.busticketpro.repository.SeatRepository;
import com.busticketpro.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public List<Trip> searchTrips(Long fromId, Long toId, LocalDate date) {
        return tripRepository.search(fromId, toId, date);
    }

    @Transactional(readOnly = true)
    public Trip getById(Long id) {
        return tripRepository.findDetailById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến xe"));
    }

    @Transactional(readOnly = true)
    public List<Seat> getSeats(Long tripId) {
        return seatRepository.findByTrip_IdOrderBySeatNumberAsc(tripId);
    }
}