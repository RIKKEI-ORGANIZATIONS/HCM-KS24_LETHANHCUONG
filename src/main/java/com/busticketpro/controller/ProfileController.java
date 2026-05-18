package com.busticketpro.controller;

import com.busticketpro.dto.ProfileRequest;
import com.busticketpro.entity.AppUser;
import com.busticketpro.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        AppUser user = userService.getByUsername(authentication.getName());

        ProfileRequest form = new ProfileRequest();
        form.setFullName(user.getFullName());
        form.setPhone(user.getPhone());
        form.setEmail(user.getEmail());
        form.setAddress(user.getAddress());

        model.addAttribute("form", form);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            Authentication authentication,
            @Valid @ModelAttribute("form") ProfileRequest form,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "profile";
        }

        userService.updateProfile(authentication.getName(), form);
        return "redirect:/profile?success";
    }
}