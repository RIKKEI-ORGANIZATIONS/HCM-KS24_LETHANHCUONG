package com.busticketpro.controller;

import com.busticketpro.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/staff/tickets")
public class StaffTicketController {

    private final TicketService ticketService;

    @GetMapping
    public String pendingTickets(Model model) {
        model.addAttribute("tickets", ticketService.findPendingTickets());
        return "staff/pending";
    }

    @PostMapping("/{ticketId}/confirm")
    public String confirm(@PathVariable Long ticketId,
                          RedirectAttributes redirectAttributes) {
        try {
            ticketService.confirmPayment(ticketId);
            redirectAttributes.addFlashAttribute("successMessage", "Xác nhận thanh toán thành công");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/staff/tickets";
    }

    @PostMapping("/{ticketId}/cancel")
    public String cancel(@PathVariable Long ticketId,
                         RedirectAttributes redirectAttributes) {
        try {
            ticketService.cancelByStaff(ticketId);
            redirectAttributes.addFlashAttribute("successMessage", "Hủy vé thành công");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/staff/tickets";
    }
}