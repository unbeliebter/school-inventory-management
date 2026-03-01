package de.schoolinventorymanagement.controller.view;

import de.schoolinventorymanagement.daos.RoleDao;
import de.schoolinventorymanagement.entities.user.RoleEntity;
import de.schoolinventorymanagement.entities.user.UserEntity;
import de.schoolinventorymanagement.service.user.PasswordHandler;
import de.schoolinventorymanagement.service.user.RoleRequest;
import de.schoolinventorymanagement.service.user.UserRequest;
import de.schoolinventorymanagement.service.user.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping({"/users"})
public class UsersPageController extends APageController<UserEntity, UserRequest> {

    public static class DTO {
        @Getter
        @Setter
        public List<UserEntity> list;
    }

    private static final Logger LOGGER = Logger.getLogger(UsersPageController.class.getName());
    private final PasswordHandler pwHandler;
    private final RoleDao roleDao;

    public UsersPageController(UserService mainService, PasswordHandler pwHandler, RoleDao roleDao) {
        this.mainService = mainService;
        this.userService = mainService;
        this.pwHandler = pwHandler;
        this.roleDao = roleDao;
        PATH = "users";
    }

    @RequestMapping({""})
    public String showTable(Authentication auth, Model model, UserEntity newTableItem, UserRequest userRequest,
                            @RequestParam(required = false) Boolean filter,
                            @RequestParam(required = false) String username,
                            @RequestParam(required = false) String firstname,
                            @RequestParam(required = false) String lastname,
                            @RequestParam(required = false) String email,
                            @RequestParam(required = false) String frontendName,
                            @RequestParam(required = false) Boolean pwResetRequested) {
        // NOTE: It's for some reason important that the pwResetRequest param does not have the same name as the entity.
        //       Otherwise it tries to map the request to a UserEntity ... and fails
        List<UserEntity> mainEntities;
        if (filter != null && filter) {
            userRequest.setUsername(username);
            userRequest.setFirstName(firstname);
            userRequest.setLastName(lastname);
            userRequest.setEmail(email);
            userRequest.setRequiresPasswordReset(pwResetRequested);

            // Well this is annoying
            RoleRequest roleRequest;
            try {
                roleRequest = new RoleRequest();
                RoleEntity role = roleDao.findByFrontendName(frontendName).orElseThrow();
                roleRequest.setId(role.getId());
                roleRequest.setName(role.getName());
                roleRequest.setFrontendName(role.getFrontendName());
            } catch (NoSuchElementException e) {
                LOGGER.log(Level.INFO, "No role to filter");
                roleRequest = null;
            }
            userRequest.setRole(roleRequest);

            mainEntities = mainService.getFilteredAsList(userRequest);
        } else {
            mainEntities = mainService.getAll();
        }

        buildGeneralModel(auth, model, newTableItem, mainEntities);

        return PATH;
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
     * Ensures this path is not used
     * @param tableItemId id of table item
     * @param newTableItem The TableItem to add to db
     * @return html page
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
        UserEntity newTableItem = mainService.getById(tableItemId);
        if (newTableItem != null) {
            fillEntityFields(newTableItem, tableItemId, username, firstname, lastname, email, roleId);
            mainService.create(newTableItem);
            return ResponseEntity.ok().body("");
        }

        newTableItem = new UserEntity();
        fillEntityFields(newTableItem, tableItemId, username, firstname, lastname, email, roleId);

        String rawPw = pwHandler.generateOneTimePassword();
        newTableItem.setPassword(pwHandler.hashPassword(rawPw));
        mainService.create(newTableItem);

        final int STATUS_CREATED = 201;
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

        return ResponseEntity.ok().body(rawPw);
    }

    @PatchMapping("/removePwResetFlag")
    @ResponseStatus
    public ResponseEntity<String> removePwResetFlag(@RequestParam("userId") String userId) {
        UserEntity user = mainService.getById(userId);
        user.setRequiresPasswordReset(false);
        mainService.create(user);

        return ResponseEntity.ok().body("");
    }

    @RequestMapping("/export")
    public void exportTableToCsv(@ModelAttribute("DTO") DTO dto, HttpServletResponse response) throws IOException {
        String fileName = PATH + "-table.csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=%s".formatted(fileName));

        mainService.writeToCsv(dto.list, response.getWriter());
    }

    private void fillEntityFields(UserEntity e,
                                  String tableItemId,
                                  String username,
                                  String firstname,
                                  String lastname,
                                  String email,
                                  String roleId) {
        e.setId(tableItemId.equals("new") ? null : tableItemId);
        e.setUsername(username);
        e.setFirstname(firstname);
        e.setLastname(lastname);
        e.setEmail(email);
        var role = roleDao.findByFrontendName(roleId).orElseThrow();
        e.setRole(role);
    }
}