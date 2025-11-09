package com.example.demo.service.organizationalGroup;

import com.example.demo.daos.OrganizationalGroupDao;
import com.example.demo.entities.OrganizationalGroupEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationalGroupService {

    @Autowired
    private OrganizationalGroupDao dao;
    @Autowired
    private OrganizationalGroupMapper mapper;

    public List<OrganizationalGroupEntity> getAll() {
        return dao.findAll();
    }

    @Transactional
    public OrganizationalGroupEntity create(OrganizationalGroupRequest request) {
        var entity = mapper.mapToEntity(request, new OrganizationalGroupEntity());
        return dao.save(entity);
    }

    public OrganizationalGroupEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }
}
