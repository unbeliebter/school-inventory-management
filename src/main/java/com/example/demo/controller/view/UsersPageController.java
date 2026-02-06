package com.example.demo.controller.view;

import com.example.demo.daos.RoleDao;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.user.PasswordHandler;
import com.example.demo.service.user.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/users"})
public class UsersPageController extends APageController<UserEntity> {

    private final PasswordHandler pwHandler;
    private final RoleDao roleDao;

    public UsersPageController(UserService mainService, PasswordHandler pwHandler, RoleDao roleDao) {
        this.mainService = mainService;
        this.pwHandler = pwHandler;
        this.roleDao = roleDao;
        PATH = "users";
    }

    @Override
    protected void addAdditionalServicesOrEntitiesToModel(Model model) {
        var roles = roleDao.findAll();
        model.addAttribute("Roles", roles);
    }


    /**
     * Ensures this paths is not used
     * @param tableItemId
     * @param newTableItem
     * @param model
     * @return
     */
    @Override
    public String submitForm(String tableItemId, UserEntity newTableItem) {
        return "redirect:/" + PATH;
    }

    @PostMapping("/addWithRole")
    @ResponseBody
    public ResponseEntity<String> submitFormWithRoleId(@RequestParam("tableItemId") String tableItemId,
                                                       @RequestParam("username") String username,
                                                       @RequestParam("firstname") String firstname,
                                                       @RequestParam("lastname") String lastname,
                                                       @RequestParam("email") String email,
                                                       @RequestParam("roleId") String roleId) {

        UserEntity newTableItem = new UserEntity();
        fillEntityFields(newTableItem, tableItemId, username, firstname, lastname, email, roleId);

        String rawPw = pwHandler.generateOneTimePassword();
        newTableItem.setPassword(pwHandler.hashPassword(rawPw));
        mainService.create(newTableItem);
        return ResponseEntity.status(201).body(rawPw);
    }


    private void fillEntityFields(UserEntity e, String tableItemId, String username, String firstname, String lastname, String email, String roleId) {
        e.setId(tableItemId.equals("new") ? null : tableItemId);
        e.setUsername(username);
        e.setFirstname(firstname);
        e.setLastname(lastname);
        e.setEmail(email);
        var role = roleDao.findByFrontendName(roleId).orElseThrow();
        e.setRole(role);
        e.setChangedPassword(false);
        e.setRequiresPasswordReset(false);
    }
}
// ,
//                                                       @ModelAttribute UserEntity newTableItem