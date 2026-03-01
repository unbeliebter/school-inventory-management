package de.schoolinventorymanagement.service.user.mapper;

import de.schoolinventorymanagement.entities.user.RoleEntity;
import de.schoolinventorymanagement.service.user.RoleRequest;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleEntity mapToEntity(RoleRequest request) {
        var entity = new RoleEntity();

        entity.setId(request.getId());
        entity.setName(request.getName());
        entity.setFrontendName(request.getFrontendName());

        return entity;
    }
}
