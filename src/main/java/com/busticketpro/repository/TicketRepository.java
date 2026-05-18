package com.busticketpro.repository;

import com.busticketpro.dto.TicketDetailDto;
import com.busticketpro.entity.Ticket;
import com.busticketpro.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByStatusOrderByBookingTimeAsc(TicketStatus status);

    Optional<Ticket> findByTicketCodeAndPhone(String ticketCode, String phone);

    @Query("""
        select new com.busticketpro.dto.TicketDetailDto(
            t.id,
            t.ticketCode,
            t.customerName,
            t.phone,
            t.email,
            b.licensePlate,
            b.busType,
            b.driverName,
            lf.name,
            lt.name,
            tr.departureTime,
            s.seatNumber,
            t.status,
            t.bookingTime,
            t.totalPrice
        )
        from Ticket t
        join t.trip tr
        join tr.bus b
        join tr.route r
        join r.fromLocation lf
        join r.toLocation lt
        join t.seat s
        where t.ticketCode = :code and t.phone = :phone
    """)
    Optional<TicketDetailDto> findDetailByTicketCodeAndPhone(@Param("code") String code,
                                                             @Param("phone") String phone);
}