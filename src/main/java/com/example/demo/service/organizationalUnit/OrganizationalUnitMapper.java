package com.example.demo.service.organizationalUnit;

import com.example.demo.entities.OrganizationalUnitEntity;
import org.springframework.stereotype.Component;

@Component
public class OrganizationalUnitMapper {

    public OrganizationalUnitEntity mapToEntity(OrganizationalUnitRequest request, OrganizationalUnitEntity entity) {
        entity.setName(request.getName());

        return entity;
    }

    public OrganizationalUnitEntity mapToEntityDetailed(OrganizationalUnitRequest request) {
        OrganizationalUnitEntity entity = new OrganizationalUnitEntity();
        entity.setId(request.getId());

        return mapToEntity(request, entity);
    }
}
