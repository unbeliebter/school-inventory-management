package com.example.demo.service;

import com.example.demo.daos.ResponsibilityDao;
import com.example.demo.entities.ResponsibilityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponisbilityService {

    @Autowired
    private ResponsibilityDao dao;

    public List<ResponsibilityEntity> getAll() {
        return dao.findAll();
    }

    public ResponsibilityEntity createOrUpdate(ResponsibilityEntity entity) {
        return dao.save(entity);
    }

    public ResponsibilityEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }
}
