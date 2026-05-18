package com.busticketpro.service;

import com.busticketpro.dto.BookingRequest;
import com.busticketpro.entity.Seat;
import com.busticketpro.entity.Ticket;
import com.busticketpro.entity.Trip;
import com.busticketpro.enums.SeatStatus;
import com.busticketpro.enums.TicketStatus;
import com.busticketpro.repository.SeatRepository;
import com.busticketpro.repository.TicketRepository;
import com.busticketpro.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final TripRepository tripRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public Ticket bookTicket(BookingRequest request) {

        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến xe"));

        // ❗ Không cho đặt vé chuyến đã chạy
        if (trip.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Chuyến xe đã khởi hành");
        }

        // ❗ LOCK GHẾ
        Seat seat = seatRepository.findLockedById(request.getSeatId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ghế"));

        if (!seat.getTrip().getId().equals(trip.getId())) {
            throw new RuntimeException("Ghế không thuộc chuyến xe này");
        }

        // ❗ CHỐNG DOUBLE BOOKING
        if (seat.getStatus() == SeatStatus.BOOKED
                || seat.getStatus() == SeatStatus.PENDING) {
            throw new RuntimeException("Ghế đã được đặt hoặc đang giữ");
        }

        // update ghế
        seat.setStatus(SeatStatus.PENDING);
        seatRepository.save(seat);

        // tạo ticket
        Ticket ticket = new Ticket();
        ticket.setTicketCode(generateCode());
        ticket.setCustomerName(request.getCustomerName());
        ticket.setPhone(request.getPhone());
        ticket.setEmail(request.getEmail());
        ticket.setTrip(trip);
        ticket.setSeat(seat);
        ticket.setTotalPrice(trip.getPrice());
        ticket.setStatus(TicketStatus.PENDING);
        ticket.setBookingTime(LocalDateTime.now());

        ticketRepository.save(ticket);

        return ticket;
    }

    private String generateCode() {
        return "TK" + System.currentTimeMillis();
    }

    public Trip getTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy chuyến xe"));
    }

    public List<Seat> getSeatsByTrip(Long tripId) {
        return seatRepository.findByTrip_IdOrderBySeatNumberAsc(tripId);
    }
}