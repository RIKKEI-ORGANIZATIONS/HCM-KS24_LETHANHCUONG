package com.busticketpro.controller;

import com.busticketpro.entity.Trip;
import com.busticketpro.repository.BusRepository;
import com.busticketpro.repository.RouteRepository;
import com.busticketpro.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/trips")
public class AdminTripController {

    private final TripService tripService;

    private final BusRepository busRepository;

    private final RouteRepository routeRepository;

    // =====================================================
    // LIST
    // =====================================================

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "trips",
                tripService.findAll()
        );

        return "admin/trips/list";
    }

    // =====================================================
    // CREATE FORM
    // =====================================================

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "trip",
                new Trip()
        );

        model.addAttribute(
                "buses",
                busRepository.findAll()
        );

        model.addAttribute(
                "routes",
                routeRepository.findAll()
        );

        return "admin/trips/form";
    }

    // =====================================================
    // SAVE
    // =====================================================

    @PostMapping("/create")
    public String save(
            @Valid @ModelAttribute("trip") Trip trip,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "buses",
                    busRepository.findAll()
            );

            model.addAttribute(
                    "routes",
                    routeRepository.findAll()
            );

            return "admin/trips/form";
        }

        try {

            tripService.save(trip);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Thêm chuyến xe thành công"
            );

            return "redirect:/admin/trips";

        } catch (RuntimeException ex) {

            model.addAttribute(
                    "buses",
                    busRepository.findAll()
            );

            model.addAttribute(
                    "routes",
                    routeRepository.findAll()
            );

            model.addAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            return "admin/trips/form";
        }
    }

    // =====================================================
    // EDIT FORM
    // =====================================================

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "trip",
                tripService.findById(id)
        );

        model.addAttribute(
                "routes",
                routeRepository.findAll()
        );

        model.addAttribute(
                "buses",
                busRepository.findAll()
        );

        return "admin/trips/form";
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("trip") Trip trip,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "routes",
                    routeRepository.findAll()
            );

            model.addAttribute(
                    "buses",
                    busRepository.findAll()
            );

            return "admin/trips/form";
        }

        try {

            tripService.save(trip);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Cập nhật chuyến xe thành công"
            );

            return "redirect:/admin/trips";

        } catch (RuntimeException ex) {

            model.addAttribute(
                    "routes",
                    routeRepository.findAll()
            );

            model.addAttribute(
                    "buses",
                    busRepository.findAll()
            );

            model.addAttribute(
                    "errorMessage",
                    ex.getMessage()
            );

            return "admin/trips/form";
        }
    }

    // =====================================================
    // DELETE
    // =====================================================

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {

        try {

            tripService.delete(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Xóa chuyến xe thành công"
            );

        } catch (RuntimeException ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );
        }

        return "redirect:/admin/trips";
    }
}