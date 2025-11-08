package com.example.demo.service;

import com.example.demo.daos.PositionDao;
import com.example.demo.entities.PositionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    @Autowired
    private PositionDao dao;

    public List<PositionEntity> getAll() {
        return dao.findAll();
    }

    public PositionEntity createOrUpdate(PositionEntity entity) {
        return dao.save(entity);
    }

    public PositionEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }
}
