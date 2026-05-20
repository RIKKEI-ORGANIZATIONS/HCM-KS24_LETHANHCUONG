package com.busticketpro.scheduler;

import com.busticketpro.entity.Seat;
import com.busticketpro.entity.Ticket;
import com.busticketpro.enums.SeatStatus;
import com.busticketpro.enums.TicketStatus;
import com.busticketpro.repository.SeatRepository;
import com.busticketpro.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExpiredTicketScheduler {

    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelExpiredTickets() {

        List<Ticket> expiredTickets =
                ticketRepository.findByStatusAndExpiredAtBefore(
                        TicketStatus.PENDING,
                        LocalDateTime.now()
                );

        for (Ticket ticket : expiredTickets) {

            ticket.setStatus(TicketStatus.CANCELLED);

            Seat seat = ticket.getSeat();

            seat.setStatus(SeatStatus.AVAILABLE);

            seatRepository.save(seat);

            ticketRepository.save(ticket);

            log.info("Auto cancelled ticket: {}", ticket.getTicketCode());
        }
    }
}