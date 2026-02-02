package com.example.demo.service.organizationalUnit;

import com.example.demo.daos.OrganizationalUnitDao;
import com.example.demo.entities.OrganizationalUnitEntity;
import com.example.demo.service.IPageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class OrganizationalUnitService implements IPageService<OrganizationalUnitEntity> {

    @Autowired
    private OrganizationalUnitDao dao;
    @Autowired
    private OrganizationalUnitMapper mapper;

    public List<OrganizationalUnitEntity> getAll() {
        return dao.findAll();
    }

    @Transactional
    public OrganizationalUnitEntity create(OrganizationalUnitRequest request) {
        var entity = mapper.mapToEntity(request, new OrganizationalUnitEntity());
        return dao.save(entity);
    }

    @Transactional
    public OrganizationalUnitEntity create(OrganizationalUnitEntity entity) {
        return dao.save(entity);
    }

    @Transactional
    public OrganizationalUnitEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }

    @Override
    public void writeToCsv(List<OrganizationalUnitEntity> entities, PrintWriter writer) {
        // TODO;
    }
}
