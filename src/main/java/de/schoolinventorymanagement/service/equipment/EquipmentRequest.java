package de.schoolinventorymanagement.service.equipment;

import de.schoolinventorymanagement.entities.equipment.EquipmentState;
import de.schoolinventorymanagement.service.organizationalGroup.OrganizationalGroupRequest;
import de.schoolinventorymanagement.service.organizationalUnit.OrganizationalUnitRequest;
import de.schoolinventorymanagement.service.position.PositionRequest;
import de.schoolinventorymanagement.service.subject.SubjectRequest;
import de.schoolinventorymanagement.service.user.UserRequest;
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
    private PositionRequest position;
    private SubjectRequest subject;
    private UserRequest responsibleUser;
    private String renter;

}
