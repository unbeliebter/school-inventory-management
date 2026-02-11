package com.example.demo.controller.view;

import com.example.demo.entities.IHasId;
import com.example.demo.service.IPageService;
import com.example.demo.service.user.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public abstract class APageController <T extends IHasId, TR> {

    // NOTE: Needs to create a DTO for csv export in each PageController
    protected String PATH;
    protected IPageService<T, TR> mainService;
    protected UserService userService;

    protected void buildGeneralModel(Authentication auth, Model model, T newTableItem, List<T> mainEntities) {
        model.addAttribute("Path", PATH);
        model.addAttribute("TableItems", mainEntities);

        addAdditionalServicesOrEntitiesToModel(model, mainEntities);

        model.addAttribute("newTableItem", newTableItem);
        model.addAttribute("newTableItemId", newTableItem.getId());

        String currentUserId = userService.findByUsername(auth.getName()).getId();
        String currentUserRole = userService.getById(currentUserId).getRole().getName();
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("currentUserRole", currentUserRole);
    }

    @PostMapping("/add")
    public String submitForm(@RequestParam("tableItemId") String tableItemId, @ModelAttribute T newTableItem) {
        newTableItem.setId(tableItemId.equals("new") ? null : tableItemId);
        mainService.create(newTableItem);
        return "redirect:/" + PATH;
    }

    @DeleteMapping("/remove")
    @ResponseBody
    public ResponseEntity<String> removeEntry(@RequestParam("tableItemId") String tableItemId) {
        try {
            mainService.deleteById(tableItemId);
            return ResponseEntity.status(200).body("Entry with id " + tableItemId + " has been deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(423).body("The entry you are trying to delete is still in use");
        }
    }

    /**
     * Convenient method to insert more stuff
     * @param model ThymeleafModel
     */
    protected void addAdditionalServicesOrEntitiesToModel(Model model, List<T> tableList) {
    }

}
