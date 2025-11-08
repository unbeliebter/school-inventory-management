package com.example.demo.service;

import com.example.demo.daos.SubjectDao;
import com.example.demo.entities.SubjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectDao dao;

    public List<SubjectEntity> getAll() {
        return dao.findAll();
    }

    public SubjectEntity createOrUpdate(SubjectEntity entity) {
        return dao.save(entity);
    }

    public SubjectEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }
}
