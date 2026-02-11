package com.example.demo.controller.view;

import com.example.demo.entities.PositionEntity;
import com.example.demo.service.position.PositionRequest;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.user.services.UserService;
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
@RequestMapping({"/positions"})
public class PositionsPageController extends APageController<PositionEntity, PositionRequest> {
    public static class DTO {
        @Getter
        @Setter
        public List<PositionEntity> list;
    }
    public PositionsPageController(PositionService mainService, UserService userService) {
        this.mainService = mainService;
        this.userService = userService;
        PATH = "positions";
    }

    @RequestMapping({""})
    public String showTable(Authentication auth, Model model, PositionEntity newTableItem, PositionRequest positionRequest,
                            @RequestParam(required = false) Boolean filter,
                            @RequestParam(required = false) String school,
                            @RequestParam(required = false) String room,
                            @RequestParam(required = false) String description) {
        List<PositionEntity> mainEntities;
        if (filter != null && filter) {
            positionRequest.setSchool(school);
            positionRequest.setRoom(room);
            positionRequest.setDescription(description);
            mainEntities = mainService.getFilteredAsList(positionRequest);
        } else {
            mainEntities = mainService.getAll();
        }

        buildGeneralModel(auth, model, newTableItem, mainEntities);

        return PATH;
    }

    @Override
    protected void addAdditionalServicesOrEntitiesToModel(Model model, List<PositionEntity> tableList) {
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
