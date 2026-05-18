package com.busticketpro.controller;

import com.busticketpro.dto.BookingRequest;
import com.busticketpro.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/book")
    public String bookTicket(
            @Valid @ModelAttribute("bookingForm") BookingRequest form,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "trip",
                    bookingService.getTripById(form.getTripId())
            );

            model.addAttribute(
                    "seats",
                    bookingService.getSeatsByTrip(form.getTripId())
            );

            return "passenger/seat-map";
        }

        try {
            bookingService.bookTicket(form);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Đặt vé thành công"
            );

            return "redirect:/";

        } catch (RuntimeException ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            return "redirect:/passenger/" + form.getTripId() + "/seats";
        }
    }
}