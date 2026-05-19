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

    // 📌 LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("buses", busService.findAll());
        return "admin/buses/list";
    }

    // 📌 FORM CREATE
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("bus", new Bus());
        return "admin/buses/form";
    }

    // 📌 SAVE (CREATE)
    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("bus") Bus bus,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "admin/buses/form";
        }

        busService.save(bus);
        return "redirect:/admin/buses";
    }

    // 📌 FORM EDIT
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Bus bus = busService.getById(id);
        model.addAttribute("bus", bus);
        return "admin/buses/form";
    }

    // 📌 UPDATE (EDIT)
    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("bus") Bus bus,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "admin/buses/form";
        }

        // đảm bảo có id
        if (bus.getId() == null) {
            throw new RuntimeException("Bus ID không hợp lệ");
        }

        busService.save(bus); // JPA auto update nếu có id
        return "redirect:/admin/buses";
    }

    // 📌 DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        busService.delete(id);
        return "redirect:/admin/buses";
    }
}