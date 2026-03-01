package de.schoolinventorymanagement.controller.view;

import de.schoolinventorymanagement.entities.SubjectEntity;
import de.schoolinventorymanagement.service.subject.SubjectRequest;
import de.schoolinventorymanagement.service.subject.SubjectService;
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
@RequestMapping({"/subjects"})
public class SubjectsPageController extends APageController<SubjectEntity, SubjectRequest> {
    public static class DTO {
        @Getter
        @Setter
        public List<SubjectEntity> list;
    }
    public SubjectsPageController(SubjectService mainService, UserService userService) {
        this.mainService = mainService;
        this.userService = userService;
        PATH = "subjects";
    }

    @RequestMapping({""})
    public String showTable(Authentication auth, Model model, SubjectEntity newTableItem, SubjectRequest subjectRequest,
                            @RequestParam(required = false) Boolean filter,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String abbreviation) {
        List<SubjectEntity> mainEntities;
        if (filter != null && filter) {
            subjectRequest.setName(name);
            subjectRequest.setAbbreviation(abbreviation);
            mainEntities = mainService.getFilteredAsList(subjectRequest);
        } else {
            mainEntities = mainService.getAll();
        }

        buildGeneralModel(auth, model, newTableItem, mainEntities);

        return PATH;
    }

    @Override
    protected void addAdditionalServicesOrEntitiesToModel(Model model, List<SubjectEntity> tableList) {
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
