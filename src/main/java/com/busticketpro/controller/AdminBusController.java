package com.busticketpro.controller;

import com.busticketpro.entity.Bus;
import com.busticketpro.service.BusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/buses")
public class AdminBusController {

    private final BusService busService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("buses", busService.findAll());
        return "admin/buses/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("bus", new Bus());
        return "admin/buses/form";
    }

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("bus") Bus bus,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "admin/buses/form";
        }

        busService.save(bus);
        return "redirect:/admin/buses";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("bus", busService.getById(id));
        return "admin/buses/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        busService.delete(id);
        return "redirect:/admin/buses";
    }
}