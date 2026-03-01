package de.schoolinventorymanagement.service.equipment.equipmentrenter;

import de.schoolinventorymanagement.entities.equipment.EquipmentEntity;
import de.schoolinventorymanagement.entities.equipment.EquipmentRenterEntity;
import org.springframework.stereotype.Component;

@Component
public class EquipmentRenterMapper {

    public EquipmentRenterEntity toEntity(EquipmentRenterEntity entity, EquipmentEntity equipment, String renter) {
        entity.setEquipment(equipment);
        entity.setRenter(renter);

        return entity;
    }
}
