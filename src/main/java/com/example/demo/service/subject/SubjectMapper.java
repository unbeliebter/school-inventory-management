package com.example.demo.service.subject;

import com.example.demo.entities.SubjectEntity;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    public SubjectEntity mapToEntity(SubjectRequest request, SubjectEntity entity) {
        entity.setName(request.getName());
        entity.setAbbreviation(request.getAbbreviation());

        return entity;
    }

    public SubjectEntity mapToEntityDetailed(SubjectRequest request) {
        var entity = new SubjectEntity();
        entity.setId(request.getId());

        return mapToEntity(request, entity);
    }
}
