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
    UserService mainService;
    final String PATH = "users";

    public UsersPageController(UserService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping({""})
    public String showIndex(Model model) {
        List<UserEntity> mainEntities = mainService.getAll();

        List<UserType> userTypes = Arrays.asList(UserType.values());

        model.addAttribute("Path", PATH);
        model.addAttribute("TableItems", mainEntities);

        model.addAttribute("UserTypes", userTypes);



        UserEntity newTableItem = new UserEntity();
        model.addAttribute("newTableItem", newTableItem);
        model.addAttribute("newTableItemId", newTableItem.getId());

        return PATH;
    }

    @PostMapping("/add")
    public String submitForm(@RequestParam("tableItemId") String tableItemId, @ModelAttribute UserEntity newTableItem, Model model) {
        newTableItem.setId(tableItemId.equals("new") ? null : tableItemId);
        mainService.create(newTableItem);
        showIndex(model);
        return "redirect:/" + PATH;
    }

    @PostMapping("/remove")
    public String removeEquipmentEntry(@RequestParam("tableItemId") String tableItemId, Model model) {
        mainService.deleteById(tableItemId);
        showIndex(model);
        return "redirect:/" + PATH;
    }
}
