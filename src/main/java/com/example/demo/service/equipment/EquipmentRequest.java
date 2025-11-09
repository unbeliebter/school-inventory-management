package com.example.demo.service.equipment;

import com.example.demo.entities.equipment.EquipmentState;
import com.example.demo.service.organizationalGroup.OrganizationalGroupRequest;
import com.example.demo.service.organizationalUnit.OrganizationalUnitRequest;
import com.example.demo.service.position.PostionRequest;
import com.example.demo.service.subject.SubjectRequest;
import com.example.demo.service.user.UserRequest;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EquipmentRequest {

    private String id;
    private String inventoryNumber;
    private String equipmentName;
    private EquipmentState state;
    private OrganizationalUnitRequest organizationalUnit;
    private OrganizationalGroupRequest organizationalGroup;
    private PostionRequest position;
    private SubjectRequest subject;
    private UserRequest responsibleUser;

}
