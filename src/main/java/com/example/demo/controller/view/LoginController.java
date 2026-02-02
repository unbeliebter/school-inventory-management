package com.example.demo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {


    public LoginController() {
    }

    @RequestMapping({"/login"})
    public String showIndex(Model model) {

        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
