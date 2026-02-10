package com.example.demo.controller.view;

import com.example.demo.entities.OrganizationalUnitEntity;
import com.example.demo.service.organizationalUnit.OrganizationalUnitRequest;
import com.example.demo.service.organizationalUnit.OrganizationalUnitService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public OrganizationalUnitsPageController(OrganizationalUnitService mainService) {
        this.mainService = mainService;
        PATH = "units";
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
