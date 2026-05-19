
        package com.busticketpro.service;

import com.busticketpro.dto.BookingRequest;
import com.busticketpro.entity.AppUser;
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
    private final UserService userService;

    @Transactional
    public Ticket bookTicket(
            BookingRequest request,
            String username
    ) {

        AppUser user = userService.getByUsername(username);

        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy chuyến xe"));

        Seat seat = seatRepository.findLockedById(request.getSeatId())
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy ghế"));

        // CHECK GHẾ THUỘC CHUYẾN
        if (!seat.getTrip().getId().equals(trip.getId())) {
            throw new RuntimeException("Ghế không thuộc chuyến xe này");
        }

        // CHECK GHẾ TRỐNG
        if (seat.getStatus() != SeatStatus.AVAILABLE) {
            throw new RuntimeException("Ghế đã được đặt");
        }

        // UPDATE GHẾ
        seat.setStatus(SeatStatus.PENDING);

        // TẠO VÉ
        Ticket ticket = new Ticket();

        ticket.setTicketCode("TK" + System.currentTimeMillis());

        // LẤY THÔNG TIN TỪ ACCOUNT
        ticket.setCustomerName(user.getFullName());
        ticket.setPhone(user.getPhone());
        ticket.setEmail(user.getEmail());

        ticket.setTrip(trip);
        ticket.setSeat(seat);

        ticket.setTotalPrice(trip.getPrice());

        ticket.setStatus(TicketStatus.PENDING);

        ticket.setBookingTime(LocalDateTime.now());
        ticket.setExpiredAt(
                LocalDateTime.now().plusMinutes(15)
        );
        ticketRepository.save(ticket);

        seatRepository.save(seat);

        return ticket;
    }

    public Trip getTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy chuyến xe"));
    }

    public List<Seat> getSeatsByTrip(Long tripId) {
        return seatRepository.findByTrip_IdOrderBySeatNumberAsc(tripId);
    }

    @Transactional
    public void cancelBooking(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy vé"));

        ticket.setStatus(TicketStatus.CANCELLED);

        Seat seat = ticket.getSeat();

        seat.setStatus(SeatStatus.AVAILABLE);

        seatRepository.save(seat);

        ticketRepository.save(ticket);
    }
}

