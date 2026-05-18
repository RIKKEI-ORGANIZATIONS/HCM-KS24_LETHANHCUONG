package com.busticketpro.controller;

import com.busticketpro.dto.BookingRequest;
import com.busticketpro.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping("/trips/{tripId}/seats")
    public String seatMap(@PathVariable Long tripId, Model model) {
        model.addAttribute("trip", tripService.getById(tripId));
        model.addAttribute("seats", tripService.getSeats(tripId));
        model.addAttribute("bookingForm", new BookingRequest(tripId, null, null, null, null));
        return "passenger/seat-map";
    }
}