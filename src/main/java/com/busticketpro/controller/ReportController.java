package com.busticketpro.controller;

import com.busticketpro.repository.AppUserRepository;
import com.busticketpro.repository.TripRepository;
import com.busticketpro.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final AppUserRepository userRepo;
    private final TripRepository tripRepo;
    private final TicketRepository ticketRepo;

    @GetMapping("/admin/report")
    public String report(Model model) {

        long totalUsers = userRepo.count();
        long totalTrips = tripRepo.count();
        long totalTickets = ticketRepo.count();

        Double totalRevenue = ticketRepo.sumRevenue();
        if (totalRevenue == null) totalRevenue = 0.0;

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalTrips", totalTrips);
        model.addAttribute("totalTickets", totalTickets);
        model.addAttribute("totalRevenue", totalRevenue);

        return "admin/report";
    }
}