package com.busticketpro.controller;

import com.busticketpro.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/tickets/search")
    public String search(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String phone,
            Model model
    ) {
        if (code != null && phone != null && !code.isBlank() && !phone.isBlank()) {
            model.addAttribute("ticket",
                    ticketService.searchDetail(code, phone).orElse(null));
            if (model.getAttribute("ticket") == null) {
                model.addAttribute("errorMessage", "Không tìm thấy vé");
            }
        }
        return "passenger/ticket-search";
    }

    @PostMapping("/tickets/{ticketId}/cancel")
    public String cancelPassenger(
            @PathVariable Long ticketId,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        try {
            ticketService.cancelByPassenger(ticketId, authentication.getName());
            redirectAttributes.addFlashAttribute("successMessage", "Hủy vé thành công");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/tickets/search";
    }
}