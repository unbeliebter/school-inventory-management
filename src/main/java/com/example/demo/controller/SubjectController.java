package com.example.demo.controller;

import com.example.demo.entities.SubjectEntity;
import com.example.demo.service.subject.SubjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.subject.SubjectService;

import java.util.List;

@RestController
@RequestMapping("api/subject/")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("getAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SubjectEntity> getAll(){
        return subjectService.getAll();
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SubjectEntity getById(@PathVariable String id){
        return subjectService.getById(id);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SubjectEntity save(@RequestBody SubjectRequest request){
        return subjectService.create(request);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable String id){
        subjectService.deleteById(id);
    }
}
