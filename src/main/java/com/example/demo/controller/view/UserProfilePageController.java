package com.example.demo.controller.view;

import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.user.PasswordHandler;
import com.example.demo.service.user.PasswordState;
import com.example.demo.service.user.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping({"/userProfile"})
public class UserProfilePageController {
    private static final Logger LOGGER = Logger.getLogger(UserProfilePageController.class.getName());
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
        model.addAttribute("pwMinLength", MIN_PASSWORD_LENGTH);

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

    @PatchMapping({"/changePassword"})
    @ResponseBody
    public ResponseEntity<String> changePassword(@RequestParam String oldPw,
                                                 @RequestParam String pw,
                                                 @RequestParam String pwCheck,
                                                 Authentication auth, BCryptPasswordEncoder bcrypt) {
        LOGGER.info("User requests Password change");
        PasswordState pwState = pwHandler.isPasswordValid(pw, pwCheck);
        if (pwState == PasswordState.EMPTY) {
            LOGGER.warning("Password state empty");
            return ResponseEntity.status(400).body("Eingabe fehlt!");
        }
        if (pwState == PasswordState.TO_SHORT) {
            LOGGER.warning("Password was to short");
            return ResponseEntity.status(400).body("Passwort zu kurz!");
        }
        if (pwState == PasswordState.UNEQUAL) {
            LOGGER.warning("Password was not equal to CheckPW");
            return ResponseEntity.status(400).body("Fehler bei Wiederholung!");
        }

        String id = userService.findByUsername(auth.getName()).getId();
        UserEntity user = userService.getById(id);
        if (!bcrypt.matches(oldPw, user.getPassword())) {
            LOGGER.warning("Old password was wrong");
            return ResponseEntity.status(400).body("Altes Passwort falsch!");
        }

        user.setPassword(pwHandler.hashPassword(pw));
        user.setChangedPassword(true);
        userService.save(user);

        return ResponseEntity.ok().body("Erfolg!");
    }
}
