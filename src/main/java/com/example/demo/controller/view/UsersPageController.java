package com.example.demo.controller.view;

import com.example.demo.daos.RoleDao;
import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.IPageService;
import com.example.demo.service.user.PasswordHandler;
import com.example.demo.service.user.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping({"/users"})
public class UsersPageController extends APageController<UserEntity> {

    class DTO {
        public List<UserEntity> list;

        public List<UserEntity> getList() {
            return list;
        }

        public void setList(List<UserEntity> list) {
            this.list = list;
        }
    }

    private final PasswordHandler pwHandler;
    private final RoleDao roleDao;
    private final int STATUS_CREATED = 201;
    private final int STATUS_OK = 200;

    public UsersPageController(UserService mainService, PasswordHandler pwHandler, RoleDao roleDao) {
        this.mainService = mainService;
        this.pwHandler = pwHandler;
        this.roleDao = roleDao;
        PATH = "users";
    }

    @Override
    protected void addAdditionalServicesOrEntitiesToModel(Model model, List<UserEntity> tableList) {
        var roles = roleDao.findAll();
        model.addAttribute("Roles", roles);

        DTO dto = new DTO();
        dto.list = tableList;
        model.addAttribute("DTO", dto);
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
        return ResponseEntity.status(STATUS_CREATED).body(rawPw);
    }

    @PostMapping("/resetPassword")
    @ResponseBody
    public ResponseEntity<String> resetUserPassword(@RequestParam("userId") String userId) {
        UserEntity user = mainService.getById(userId);
        String rawPw = pwHandler.generateOneTimePassword();
        user.setPassword(pwHandler.hashPassword(rawPw));
        user.setRequiresPasswordReset(false);
        mainService.create(user);
        // TODO Need two calls to Service.create because the database script sets automatically the changed_password flag to true
        // TODO Actually it's even fucking worse. It Creates a RaceCondition which i need to circumvent with the following fuckery
        // This sucks
        Runnable runnable = () -> {
            try {
                Thread.sleep(2000);
                user.setChangedPassword(false);
                mainService.create(user);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        new Thread(runnable).start();

        return ResponseEntity.status(STATUS_OK).body(rawPw);
    }

    @RequestMapping("/export")
    public void exportTableToCsv(@ModelAttribute("DTO") DTO dto, HttpServletResponse response) throws IOException {
        String fileName = PATH + "-table.csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=%s".formatted(fileName));

        mainService.writeToCsv(dto.list, response.getWriter());
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