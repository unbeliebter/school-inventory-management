package com.example.demo.controller.view;

import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.user.PasswordHandler;
import com.example.demo.service.user.PasswordState;
import com.example.demo.service.user.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/userProfile"})
public class UserProfilePageController {
    private final int STATUS_NOT_FOUND = 404;

    private final int MIN_PASSWORD_LENGTH = 12;
    private final UserService userService;
    private final PasswordHandler pwHandler;

    public UserProfilePageController(UserService userService, PasswordHandler pwHandler) {
        this.userService = userService;
        this.pwHandler = pwHandler;
    }

    @GetMapping({""})
    public String showProfile(Model model, Authentication auth) {
        String id = userService.findByUsername(auth.getName()).getId();
        UserEntity user = userService.getById(id);

        model.addAttribute("user",user);
        model.addAttribute("USER_ID", user.getId());

        return "userProfile";
    }

    @PatchMapping({"/changeValue"})
    @ResponseBody
    public ResponseEntity<String> changeValue(@RequestParam String userId,
                                              @RequestParam(required = false) String firstname,
                                              @RequestParam(required = false) String lastname,
                                              @RequestParam(required = false) String email) {
        UserEntity user = userService.getById(userId);
        if (firstname != null) {
            user.setFirstname(firstname);
        }
        if (lastname != null) {
            user.setLastname(lastname);
        }
        if (email != null) {
            user.setEmail(email);
        }

        userService.save(user);

        return ResponseEntity.ok().body("Value should be changed");
    }

    @RequestMapping({"/changePassword"})
    public String changePassword(@RequestParam(required = false) String pw,
                                      @RequestParam(required = false) String pwCheck,
                                      Authentication auth, Model model) {
        model.addAttribute("minLength", MIN_PASSWORD_LENGTH);
        PasswordState pwState = pwHandler.isPasswordValid(pw, pwCheck);
        if (pwState == PasswordState.EMPTY) {
            return "userProfile";
        }
        if (pwState == PasswordState.TO_SHORT) {
            model.addAttribute("errorLength", true);
            return "userProfile";
        }
        if (pwState == PasswordState.UNEQUAL) {
            model.addAttribute("errorPwDifferent", true);
            return "userProfile";
        }

        String id = userService.findByUsername(auth.getName()).getId();
        UserEntity user = userService.getById(id);

        user.setPassword(pwHandler.hashPassword(pw));
        user.setChangedPassword(true);
        userService.save(user);

        return "userProfile";
    }
}
