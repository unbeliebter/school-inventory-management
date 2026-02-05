package com.example.demo.service.equipment.equipmentrenter;

import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentRenterEntity;
import org.springframework.stereotype.Component;

@Component
public class EquipmentRenterMapper {

    public EquipmentRenterEntity toEntity(EquipmentRenterEntity entity, EquipmentEntity equipment, String renter) {
        entity.setEquipment(equipment);
        entity.setRenter(renter);

        return entity;
    }
}
