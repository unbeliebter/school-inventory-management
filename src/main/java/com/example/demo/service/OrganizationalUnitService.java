package com.example.demo.service;

import com.example.demo.daos.OrganizationalUnitDao;
import com.example.demo.entities.OrganizationalUnitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationalUnitService {

    @Autowired
    private OrganizationalUnitDao dao;

    public List<OrganizationalUnitEntity> getAll() {
        return dao.findAll();
    }

    public OrganizationalUnitEntity createOrUpdate(OrganizationalUnitEntity entity) {
        return dao.save(entity);
    }

    public OrganizationalUnitEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }
}
