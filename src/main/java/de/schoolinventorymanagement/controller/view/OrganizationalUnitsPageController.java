package de.schoolinventorymanagement.controller.view;

import de.schoolinventorymanagement.entities.OrganizationalUnitEntity;
import de.schoolinventorymanagement.service.organizationalUnit.OrganizationalUnitRequest;
import de.schoolinventorymanagement.service.organizationalUnit.OrganizationalUnitService;
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
@RequestMapping({"/units"})
public class OrganizationalUnitsPageController extends APageController<OrganizationalUnitEntity, OrganizationalUnitRequest> {
    public static class DTO {
        @Getter
        @Setter
        public List<OrganizationalUnitEntity> list;
    }
    public OrganizationalUnitsPageController(OrganizationalUnitService mainService, UserService userService) {
        this.mainService = mainService;
        this.userService = userService;
        PATH = "units";
    }

    @RequestMapping({""})
    public String showTable(Authentication auth, Model model, OrganizationalUnitEntity newTableItem, OrganizationalUnitRequest organizationalUnitRequest,
                            @RequestParam(required = false) Boolean filter,
                            @RequestParam(required = false) String name) {
        List<OrganizationalUnitEntity> mainEntities;
        if (filter != null && filter) {
            organizationalUnitRequest.setName(name);
            mainEntities = mainService.getFilteredAsList(organizationalUnitRequest);
        } else {
            mainEntities = mainService.getAll();
        }

        buildGeneralModel(auth, model, newTableItem, mainEntities);

        return PATH;
    }

    @Override
    protected void addAdditionalServicesOrEntitiesToModel(Model model, List<OrganizationalUnitEntity> tableList) {
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
