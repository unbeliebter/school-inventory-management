package com.example.demo.service.equipment.equipmentrenter;

import com.example.demo.daos.EquipmentRenterDao;
import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentRenterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EquipmentRenterService {

    @Autowired
    private EquipmentRenterDao equipmentRenterDao;
    @Autowired
    private EquipmentRenterMapper mapper;

    public EquipmentRenterEntity create(EquipmentEntity equipment, String renter) {
        var toCreate = mapper.toEntity(new EquipmentRenterEntity(), equipment, renter);
        return equipmentRenterDao.save(toCreate);
    }

    public void delete(EquipmentEntity equipment) {
        var toDelete = equipmentRenterDao.findByEquipment(equipment);
        if (toDelete.isEmpty()) {
            throw new IllegalStateException("Es wurde kein Eintrag zum löschen gefunden.");
        }

        equipmentRenterDao.delete(toDelete.get());
    }

    public Optional<EquipmentRenterEntity> getByEquipment(EquipmentEntity equipment) {
        return equipmentRenterDao.findByEquipment(equipment);
    }
}
