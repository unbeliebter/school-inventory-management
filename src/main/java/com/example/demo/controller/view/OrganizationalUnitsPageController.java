package com.example.demo.controller.view;

import com.example.demo.entities.OrganizationalUnitEntity;
import com.example.demo.service.organizationalUnit.OrganizationalUnitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/units"})
public class OrganizationalUnitsPageController extends APageController<OrganizationalUnitEntity> {

    public OrganizationalUnitsPageController(OrganizationalUnitService mainService) {
        this.mainService = mainService;
        PATH = "units";
    }

}
