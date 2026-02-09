package com.example.demo.controller.view;

import com.example.demo.entities.OrganizationalGroupEntity;
import com.example.demo.service.organizationalGroup.OrganizationalGroupService;
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
@RequestMapping({"/groups"})
public class OrganizationalGroupsPageController extends APageController<OrganizationalGroupEntity> {
    public static class DTO {
        @Getter
        @Setter
        public List<OrganizationalGroupEntity> list;
    }
    public OrganizationalGroupsPageController(OrganizationalGroupService mainService) {
        this.mainService = mainService;
        PATH = "groups";
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
