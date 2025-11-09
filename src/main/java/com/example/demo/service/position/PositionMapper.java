package com.example.demo.service.position;

import com.example.demo.entities.PositionEntity;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {

    public PositionEntity mapToEntity(PostionRequest request, PositionEntity entity) {
        entity.setSchool(request.getSchool());
        entity.setRoom(request.getRoom());
        entity.setDescription(request.getDescription());

        return entity;
    }

    public PositionEntity mapToEntityDetailed(PostionRequest request) {
        var entity = new PositionEntity();
        entity.setId(request.getId());

        return mapToEntity(request, entity);
    }
}
