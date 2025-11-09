package com.example.demo.service.organizationalGroup;

import com.example.demo.entities.OrganizationalGroupEntity;
import org.springframework.stereotype.Component;

@Component
public class OrganizationalGroupMapper {

    public OrganizationalGroupEntity mapToEntity(OrganizationalGroupRequest request, OrganizationalGroupEntity entity) {
        entity.setName(request.getName());

        return entity;
    }

    public OrganizationalGroupEntity mapToEntityDetailed(OrganizationalGroupRequest request) {
        var entity = new OrganizationalGroupEntity();
        entity.setId(request.getId());

        return mapToEntity(request, entity);
    }
}
