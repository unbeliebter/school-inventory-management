package com.example.demo.controller;

import com.example.demo.entities.SubjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("subject/")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("getAll")
    public List<SubjectEntity> getAll(){
        return subjectService.getAll();
    }

    @GetMapping("get/{id}")
    public SubjectEntity getById(@PathVariable String id){
        return subjectService.getById(id);
    }

    @PostMapping("save")
    public SubjectEntity save(@RequestBody SubjectEntity entity){
        return subjectService.createOrUpdate(entity);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable String id){
        subjectService.deleteById(id);
    }
}
