package com.example.demo.service.user.mapper;

import com.example.demo.entities.user.RoleEntity;
import com.example.demo.entities.user.UserType;
import com.example.demo.service.user.RoleRequest;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleEntity mapToEntity(RoleRequest request) {
        var entity = new RoleEntity();

        entity.setId(request.getId());
        entity.setName(UserType.fromLabel(request.getName()));

        return entity;
    }
}
