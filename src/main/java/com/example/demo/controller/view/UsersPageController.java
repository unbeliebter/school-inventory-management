package com.example.demo.controller.view;

import com.example.demo.entities.user.RoleEntity;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.entities.user.UserType;
import com.example.demo.service.user.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping({"/users"})
public class UsersPageController extends APageController<UserEntity> {

    public UsersPageController(UserService mainService) {
        this.mainService = mainService;
        PATH = "users";
    }

    @Override
    protected void addAdditionalServicesOrEntitiesToModel(Model model) {
        List<UserType> userTypes = Arrays.asList(UserType.values());
        model.addAttribute("UserTypes", userTypes);
    }


    /**
     * Ensures this paths is not used
     * @param tableItemId
     * @param newTableItem
     * @param model
     * @return
     */
    @Override
    public String submitForm(String tableItemId, UserEntity newTableItem, Model model) {
        return "redirect:/" + PATH;
    }

    @PostMapping("/addWithRole")
    public String submitFormWithRoleId(@RequestParam("tableItemId") String tableItemId,
                             @RequestParam("roleId") String roleId,
                             @ModelAttribute UserEntity newTableItem,
                             Model model) {

        RoleEntity role = new RoleEntity();
        role.setId(roleId);
        role.setName(roleId.toUpperCase());
        newTableItem.setRole(role);

        newTableItem.setId(tableItemId.equals("new") ? null : tableItemId);
        mainService.create(newTableItem);
        return "redirect:/" + PATH;
    }
}
