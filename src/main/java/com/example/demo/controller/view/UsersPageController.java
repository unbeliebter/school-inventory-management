package com.example.demo.controller.view;

import com.example.demo.entities.SubjectEntity;
import com.example.demo.entities.equipment.EquipmentState;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.entities.user.UserType;
import com.example.demo.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping({"/users"})
public class UsersPageController {
    UserService userService;
    String PATH = "/users";

    public UsersPageController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping({""})
    public String showIndex(Model model) {
        List<UserEntity> subjects = userService.getAll();

        List<UserType> userTypes = Arrays.asList(UserType.values());

        model.addAttribute("Path", PATH);
        model.addAttribute("TableItems", subjects);

        model.addAttribute("UserTypes", userTypes);



        UserEntity newTableItem = new UserEntity();
        model.addAttribute("newTableItem", newTableItem);
        model.addAttribute("newTableItemId", newTableItem.getId());

        return "users";
    }

    @PostMapping("/add")
    public String submitForm(@RequestParam("tableItemId") String tableItemId, @ModelAttribute UserEntity newTableItem, Model model) {
        newTableItem.setId(tableItemId.equals("new") ? null : tableItemId);
        userService.create(newTableItem);
        showIndex(model);
        return "redirect:" + PATH;
    }

    @PostMapping("/remove")
    public String removeEquipmentEntry(@RequestParam("tableItemId") String tableItemId, Model model) {
        userService.deleteById(tableItemId);
        showIndex(model);
        return "redirect:" + PATH;
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
