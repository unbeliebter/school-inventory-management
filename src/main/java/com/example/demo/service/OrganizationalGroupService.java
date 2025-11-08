package com.example.demo.service;

import com.example.demo.daos.OrganizationalGroupDao;
import com.example.demo.entities.OrganizationalGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationalGroupService {

    @Autowired
    private OrganizationalGroupDao dao;

    public List<OrganizationalGroupEntity> getAll() {
        return dao.findAll();
    }

    public OrganizationalGroupEntity createOrUpdate(OrganizationalGroupEntity entity) {
        return dao.save(entity);
    }

    public OrganizationalGroupEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }
}
