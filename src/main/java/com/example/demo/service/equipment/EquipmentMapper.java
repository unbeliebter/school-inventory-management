package com.example.demo.service.equipment;

import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.service.organizationalGroup.OrganizationalGroupMapper;
import com.example.demo.service.organizationalUnit.OrganizationalUnitMapper;
import com.example.demo.service.position.PositionMapper;
import com.example.demo.service.subject.SubjectMapper;
import com.example.demo.service.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {

    @Autowired
    private OrganizationalGroupMapper organizationalGroupMapper;
    @Autowired
    private OrganizationalUnitMapper organizationalUnitMapper;
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private UserMapper userMapper;

    public EquipmentEntity mapToEntity(EquipmentRequest request, EquipmentEntity entity) {
        entity.setInventoryNumber(request.getInventoryNumber());
        entity.setEquipmentName(request.getEquipmentName());
        entity.setEquipmentState(request.getState());
        entity.setOrganizationalGroup(organizationalGroupMapper.mapToEntityDetailed(request.getOrganizationalGroup()));
        entity.setOrganizationalUnit(organizationalUnitMapper.mapToEntityDetailed(request.getOrganizationalUnit()));
        entity.setPosition(positionMapper.mapToEntityDetailed(request.getPosition()));
        entity.setSubject(subjectMapper.mapToEntityDetailed(request.getSubject()));
        entity.setResponsibleUser(userMapper.mapToEntityDetailed(request.getResponsibleUser()));

        return entity;
    }

    public EquipmentEntity mapToEntityDetailed(EquipmentRequest request) {
        var entity = new EquipmentEntity();
        entity.setId(request.getId());

        return mapToEntity(request, entity);
    }
}
