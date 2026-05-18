package com.busticketpro.dto;

import com.busticketpro.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TicketDetailDto {

    private Long ticketId;
    private String ticketCode;
    private String customerName;
    private String phone;
    private String email;

    private String licensePlate;
    private String busType;
    private String driverName;

    private String fromLocation;
    private String toLocation;
    private LocalDateTime departureTime;

    private String seatNumber;
    private TicketStatus status;
    private LocalDateTime bookingTime;
    private Double totalPrice;
}