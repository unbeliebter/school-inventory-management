package com.example.demo.controller.view;

import com.example.demo.entities.OrganizationalGroupEntity;
import com.example.demo.service.organizationalGroup.OrganizationalGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/groups"})
public class OrganizationalGroupsPageController extends APageController<OrganizationalGroupEntity> {

    public OrganizationalGroupsPageController(OrganizationalGroupService mainService) {
        this.mainService = mainService;
        PATH = "groups";
    }

}
