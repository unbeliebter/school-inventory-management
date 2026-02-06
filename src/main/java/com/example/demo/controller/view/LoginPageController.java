package com.example.demo.controller.view;

import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.user.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {

    private final UserService userService;

    public LoginPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/login"})
    public String showLogin(Model model) {

        return "login";
    }

    @RequestMapping({"/login_redirect"})
    public String redirect(Authentication auth) {
        String id = userService.findByUsername(auth.getName()).getId();
        UserEntity user = userService.getById(id);
        if (!user.isChangedPassword()) {
            System.out.println("Needs to set credentials");
            user.setChangedPassword(true);
            userService.save(user);
        }

        return "redirect:/inventory";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
