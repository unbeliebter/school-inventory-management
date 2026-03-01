package de.schoolinventorymanagement.service.equipment.equipmentrenter;

import de.schoolinventorymanagement.daos.EquipmentRenterDao;
import de.schoolinventorymanagement.entities.equipment.EquipmentEntity;
import de.schoolinventorymanagement.entities.equipment.EquipmentRenterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentRenterService {

    @Autowired
    private EquipmentRenterDao equipmentRenterDao;
    @Autowired
    private EquipmentRenterMapper mapper;

    public List<EquipmentRenterEntity> getAll() {
        return equipmentRenterDao.findAll();
    }

    public EquipmentRenterEntity create(EquipmentEntity equipment, String renter) {
        var toCreate = mapper.toEntity(new EquipmentRenterEntity(), equipment, renter);
        return equipmentRenterDao.save(toCreate);
    }

    public EquipmentRenterEntity getById(String id) {
        return equipmentRenterDao.findById(id).orElse(null);
    }

    public void delete(EquipmentEntity equipment) throws  IllegalStateException {
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
