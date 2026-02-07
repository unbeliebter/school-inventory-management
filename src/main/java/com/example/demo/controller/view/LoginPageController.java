package com.example.demo.controller.view;

import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.user.PasswordHandler;
import com.example.demo.service.user.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginPageController {

    private final int MIN_PASSWORD_LENGTH = 12;
    private final UserService userService;
    private final PasswordHandler pwHandler;

    public LoginPageController(UserService userService, PasswordHandler pwHandler) {
        this.userService = userService;
        this.pwHandler = pwHandler;
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

            return "redirect:/setInitialPassword";
        }

        return "redirect:/inventory";
    }

    @RequestMapping({"/setInitialPassword"})
    public String forcePasswordChange(@RequestParam(required = false) String pw,
                                      @RequestParam(required = false) String pwCheck,
                                      Authentication auth, Model model) {
        model.addAttribute("minLength", MIN_PASSWORD_LENGTH);
        if (pw == null || pwCheck == null) {
            return "setInitialPassword";
        }
        if (pw.length() < 12) {
            model.addAttribute("errorLength", true);
            return "setInitialPassword";
        }
        if (!pw.equals(pwCheck)) {
            model.addAttribute("errorPwDifferent", true);
            return "setInitialPassword";
        }

        String id = userService.findByUsername(auth.getName()).getId();
        UserEntity user = userService.getById(id);

        user.setPassword(pwHandler.hashPassword(pw));
        user.setChangedPassword(true);
        userService.save(user);

        return "redirect:/inventory";
    }

    @RequestMapping({"/requestPasswordReset"})
    public String requestPasswordReset() {
        return "resetPassword";
    }

    @RequestMapping({"/requestPasswordReset/send"})
    @ResponseBody
    public ResponseEntity<String> sendResetRequest(@RequestParam("username") String username) {
        UserEntity user;
        try {
            user = userService.findByUsername(username);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body("User not found!");
        }

        user.setRequiresPasswordReset(true);
        userService.save(user);

        return ResponseEntity.accepted().body("Done!");
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}
