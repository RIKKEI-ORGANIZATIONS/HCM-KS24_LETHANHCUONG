package com.busticketpro.controller;

import com.busticketpro.entity.Route;
import com.busticketpro.service.LocationService;
import com.busticketpro.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/routes")
public class AdminRouteController {

    private final RouteService routeService;
    private final LocationService locationService;

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "routes",
                routeService.findAll()
        );

        return "admin/routes";
    }

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute("route", new Route());

        model.addAttribute(
                "locations",
                locationService.findAll()
        );

        return "admin/route-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Route route) {

        routeService.save(route);

        return "redirect:/admin/routes";
    }

    @GetMapping("/{id}/edit")
    public String editForm(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "route",
                routeService.getById(id)
        );

        model.addAttribute(
                "locations",
                locationService.findAll()
        );

        return "admin/route-form";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {

        routeService.delete(id);

        return "redirect:/admin/routes";
    }
}