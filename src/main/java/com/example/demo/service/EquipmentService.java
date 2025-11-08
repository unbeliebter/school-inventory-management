package com.example.demo.service;

import com.example.demo.daos.EquipmentDao;
import com.example.demo.entities.equipment.EquipmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentDao dao;

    public List<EquipmentEntity> getAll() {
        return dao.findAll();
    }

    public EquipmentEntity createOrUpdate(EquipmentEntity entity) {
        return dao.save(entity);
    }

    public EquipmentEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }
}
