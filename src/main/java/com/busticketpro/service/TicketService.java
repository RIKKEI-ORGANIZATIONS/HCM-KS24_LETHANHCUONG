package com.busticketpro.service;

import com.busticketpro.dto.TicketDetailDto;
import com.busticketpro.entity.AppUser;
import com.busticketpro.entity.Seat;
import com.busticketpro.entity.Ticket;
import com.busticketpro.enums.SeatStatus;
import com.busticketpro.enums.TicketStatus;
import com.busticketpro.repository.SeatRepository;
import com.busticketpro.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public Optional<TicketDetailDto> searchDetail(String code, String phone) {
        return ticketRepository.findDetailByTicketCodeAndPhone(code, phone);
    }

    @Transactional(readOnly = true)
    public List<Ticket> findPendingTickets() {
        return ticketRepository.findByStatusOrderByBookingTimeAsc(TicketStatus.PENDING);
    }

    @Transactional
    public void confirmPayment(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé"));

        if (ticket.getStatus() != TicketStatus.PENDING) {
            throw new RuntimeException("Chỉ xác nhận vé chờ thanh toán");
        }

        ticket.setStatus(TicketStatus.PAID);
        Seat seat = ticket.getSeat();
        seat.setStatus(SeatStatus.BOOKED);

        seatRepository.save(seat);
        ticketRepository.save(ticket);
    }

    @Transactional
    public void cancelByStaff(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé"));

        if (ticket.getStatus() != TicketStatus.PENDING) {
            throw new RuntimeException("Chỉ hủy vé chờ thanh toán");
        }

        ticket.setStatus(TicketStatus.CANCELLED);
        Seat seat = ticket.getSeat();
        seat.setStatus(SeatStatus.AVAILABLE);

        seatRepository.save(seat);
        ticketRepository.save(ticket);
    }

    @Transactional
    public void cancelByPassenger(Long ticketId, String username) {

        AppUser user = userService.getByUsername(username);

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé"));

        // ❗ CHECK ROLE
        if (!user.getRole().name().equals("PASSENGER")) {
            throw new RuntimeException("Không có quyền");
        }

        // ❗ CHECK ĐÚNG NGƯỜI
        if (!Objects.equals(ticket.getPhone(), user.getPhone())) {
            throw new RuntimeException("Bạn không có quyền hủy vé này");
        }

        // ❗ CHỈ HỦY PENDING
        if (ticket.getStatus() != TicketStatus.PENDING) {
            throw new RuntimeException("Chỉ hủy vé chưa thanh toán");
        }

        // ❗ CHECK 12H
        if (LocalDateTime.now().isAfter(ticket.getTrip().getDepartureTime().minusHours(12))) {
            throw new RuntimeException("Chỉ được hủy trước 12 giờ khởi hành");
        }

        // update
        ticket.setStatus(TicketStatus.CANCELLED);

        Seat seat = ticket.getSeat();
        seat.setStatus(SeatStatus.AVAILABLE);

        seatRepository.save(seat);
        ticketRepository.save(ticket);
    }
}