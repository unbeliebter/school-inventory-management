package com.example.demo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginPageController {


    public LoginPageController() {
    }

    @GetMapping({"/login"})
    public String showLogin(Model model) {

        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
