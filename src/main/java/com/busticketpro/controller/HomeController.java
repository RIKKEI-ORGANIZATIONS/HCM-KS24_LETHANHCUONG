package com.busticketpro.controller;

import com.busticketpro.repository.LocationRepository;
import com.busticketpro.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final LocationRepository locationRepository;
    private final TripService tripService;

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) Long fromId,
            @RequestParam(required = false) Long toId,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate date,
            Model model
    ) {
        model.addAttribute("locations", locationRepository.findAll());
        model.addAttribute("trips", tripService.searchTrips(fromId, toId, date));
        model.addAttribute("fromId", fromId);
        model.addAttribute("toId", toId);
        model.addAttribute("date", date);
        return "home";
    }
}