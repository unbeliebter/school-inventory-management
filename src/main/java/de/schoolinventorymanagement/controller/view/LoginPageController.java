package de.schoolinventorymanagement.controller.view;

import de.schoolinventorymanagement.entities.user.UserEntity;
import de.schoolinventorymanagement.service.user.PasswordHandler;
import de.schoolinventorymanagement.service.user.PasswordState;
import de.schoolinventorymanagement.service.user.services.UserService;
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
    private final int STATUS_NOT_FOUND = 404;

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
            return "redirect:/setInitialPassword";
        }

        return "redirect:/inventory";
    }

    @RequestMapping({"/setInitialPassword"})
    public String forcePasswordChange(@RequestParam(required = false) String pw,
                                      @RequestParam(required = false) String pwCheck,
                                      Authentication auth, Model model) {
        model.addAttribute("minLength", pwHandler.MIN_PASSWORD_LENGTH);

        PasswordState pwState = pwHandler.isPasswordValid(pw, pwCheck);
        if (pwState == PasswordState.EMPTY) {
            return "setInitialPassword";
        }
        if (pwState == PasswordState.TO_SHORT) {
            model.addAttribute("errorLength", true);
            return "setInitialPassword";
        }
        if (pwState == PasswordState.UNEQUAL) {
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
            return ResponseEntity.status(STATUS_NOT_FOUND).body("User not found!");
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
