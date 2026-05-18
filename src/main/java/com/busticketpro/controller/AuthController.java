package com.busticketpro.controller;

import com.busticketpro.dto.RegisterRequest;
import com.busticketpro.service.AuthService;
import com.busticketpro.repository.AppUserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppUserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("form") RegisterRequest form,
            BindingResult result,
            Model model
    ) {
        if (userRepository.existsByUsername(form.getUsername())) {
            result.rejectValue("username", "error.username", "Username đã tồn tại");
        }

        if (result.hasErrors()) {
            return "register";
        }

        authService.registerPassenger(form);
        return "redirect:/login?registerSuccess";
    }
}