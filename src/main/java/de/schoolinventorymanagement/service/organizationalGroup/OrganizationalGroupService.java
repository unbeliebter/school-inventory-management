package de.schoolinventorymanagement.service.organizationalGroup;

import de.schoolinventorymanagement.daos.OrganizationalGroupDao;
import de.schoolinventorymanagement.entities.OrganizationalGroupEntity;
import de.schoolinventorymanagement.service.IPageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class OrganizationalGroupService implements IPageService<OrganizationalGroupEntity, OrganizationalGroupRequest> {

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

    @Transactional
    public OrganizationalGroupEntity create(OrganizationalGroupEntity entity) {
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

    @Override
    public void writeToCsv(List<OrganizationalGroupEntity> entities, PrintWriter writer) {
        writer.println("Name");

        for (OrganizationalGroupEntity e : entities) {
            String sb = e.getName();

            writer.println(sb);
        }
    }

    @Override
    public List<OrganizationalGroupEntity> getFilteredAsList(OrganizationalGroupRequest request) {
        List<OrganizationalGroupEntity> list = dao.findAll();

        String name = request.getName().isEmpty() ? null : request.getName();
        list = list.stream()
                .filter(e -> name == null || e.getName().equals(name))
                .toList();
        return list;
    }
}
