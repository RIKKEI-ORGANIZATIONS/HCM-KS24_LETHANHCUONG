package com.busticketpro.controller;

import com.busticketpro.dto.BookingRequest;
import com.busticketpro.entity.AppUser;
import com.busticketpro.service.TripService;
import com.busticketpro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    private final UserService userService;

    @GetMapping("/trips/{tripId}/seats")
    public String seatMap(
            @PathVariable Long tripId,
            Model model,
            Authentication authentication
    ) {

        model.addAttribute(
                "trip",
                tripService.getById(tripId)
        );

        model.addAttribute(
                "seats",
                tripService.getSeats(tripId)
        );

        BookingRequest bookingForm = new BookingRequest();

        bookingForm.setTripId(tripId);

        // Nếu đã login thì tự fill thông tin
        if (authentication != null) {

            AppUser user =
                    userService.getByUsername(authentication.getName());

            bookingForm.setCustomerName(user.getFullName());
            bookingForm.setPhone(user.getPhone());
            bookingForm.setEmail(user.getEmail());
        }

        model.addAttribute("bookingForm", bookingForm);

        return "passenger/seat-map";
    }
}