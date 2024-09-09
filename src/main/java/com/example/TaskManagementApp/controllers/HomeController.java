package com.example.TaskManagementApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);
        return "home";
    }
}
