package de.schoolinventorymanagement.controller.view;

import de.schoolinventorymanagement.entities.OrganizationalGroupEntity;
import de.schoolinventorymanagement.service.organizationalGroup.OrganizationalGroupRequest;
import de.schoolinventorymanagement.service.organizationalGroup.OrganizationalGroupService;
import de.schoolinventorymanagement.service.user.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping({"/groups"})
public class OrganizationalGroupsPageController extends APageController<OrganizationalGroupEntity, OrganizationalGroupRequest> {
    public static class DTO {
        @Getter
        @Setter
        public List<OrganizationalGroupEntity> list;
    }
    public OrganizationalGroupsPageController(OrganizationalGroupService mainService, UserService userService) {
        this.mainService = mainService;
        this.userService = userService;
        PATH = "groups";
    }

    @RequestMapping({""})
    public String showTable(Authentication auth, Model model, OrganizationalGroupEntity newTableItem, OrganizationalGroupRequest organizationalGroupRequest,
                            @RequestParam(required = false) Boolean filter,
                            @RequestParam(required = false) String name) {
        List<OrganizationalGroupEntity> mainEntities;
        if (filter != null && filter) {
            organizationalGroupRequest.setName(name);
            mainEntities = mainService.getFilteredAsList(organizationalGroupRequest);
        } else {
            mainEntities = mainService.getAll();
        }

        buildGeneralModel(auth, model, newTableItem, mainEntities);

        return PATH;
    }

    @Override
    protected void addAdditionalServicesOrEntitiesToModel(Model model, List<OrganizationalGroupEntity> tableList) {
        DTO dto = new DTO();
        dto.list = tableList;
        model.addAttribute("DTO", dto);
    }

    @RequestMapping("/export")
    public void exportTableToCsv(@ModelAttribute("DTO") DTO dto, HttpServletResponse response) throws IOException {
        String fileName = PATH + "-table.csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=%s".formatted(fileName));

        mainService.writeToCsv(dto.list, response.getWriter());
    }

}
