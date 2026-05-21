package com.busticketpro.service;

import com.busticketpro.entity.Bus;
import com.busticketpro.entity.Route;
import com.busticketpro.entity.Seat;
import com.busticketpro.entity.Trip;
import com.busticketpro.enums.SeatStatus;
import com.busticketpro.repository.BusRepository;
import com.busticketpro.repository.RouteRepository;
import com.busticketpro.repository.SeatRepository;
import com.busticketpro.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final SeatRepository seatRepository;

    private final BusRepository busRepository;
    private final RouteRepository routeRepository;

    // =====================================================
    // SEARCH TRIP
    // =====================================================

    @Transactional(readOnly = true)
    public List<Trip> searchTrips(
            Long fromId,
            Long toId,
            LocalDate date
    ) {

        return tripRepository.search(
                fromId,
                toId,
                date
        );
    }

    // =====================================================
    // DETAIL
    // =====================================================

    @Transactional(readOnly = true)
    public Trip getById(Long id) {

        return tripRepository.findDetailById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy chuyến xe"));
    }

    // =====================================================
    // FIND BY ID (ADMIN EDIT)
    // =====================================================

    @Transactional(readOnly = true)
    public Trip findById(Long id) {

        return tripRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy chuyến xe"));
    }

    // =====================================================
    // GET SEATS
    // =====================================================

    @Transactional(readOnly = true)
    public List<Seat> getSeats(Long tripId) {

        return seatRepository
                .findByTrip_IdOrderBySeatNumberAsc(tripId);
    }

    // =====================================================
    // ADMIN CRUD
    // =====================================================

    @Transactional(readOnly = true)
    public List<Trip> findAll() {

        return tripRepository.findAll();
    }

    @Transactional
    public void save(Trip trip) {

        boolean isNew = (trip.getId() == null);

        Route route = routeRepository.findById(
                        trip.getRoute().getId()
                )
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy tuyến"));

        Bus bus = busRepository.findById(
                        trip.getBus().getId()
                )
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy xe"));

        trip.setRoute(route);
        trip.setBus(bus);

        Trip savedTrip = tripRepository.save(trip);

        // =====================================================
        // AUTO CREATE SEATS KHI THÊM CHUYẾN MỚI
        // =====================================================

        if (isNew) {

            List<Seat> seats = new ArrayList<>();

            for (int i = 1; i <= bus.getTotalSeats(); i++) {

                Seat seat = new Seat();

                seat.setTrip(savedTrip);

                seat.setSeatNumber(String.format("%02d", i));

                seat.setStatus(SeatStatus.AVAILABLE);

                seats.add(seat);
            }

            seatRepository.saveAll(seats);
        }
    }

    // =====================================================
    // DELETE
    // =====================================================

    @Transactional
    public void delete(Long id) {

        Trip trip = tripRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy chuyến"));

        // xóa ghế trước
        seatRepository.deleteAll(
                seatRepository.findByTrip_IdOrderBySeatNumberAsc(id)
        );

        tripRepository.delete(trip);
    }
}