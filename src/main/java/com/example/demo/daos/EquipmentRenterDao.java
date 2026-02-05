package com.example.demo.daos;

import com.example.demo.entities.equipment.EquipmentEntity;
import com.example.demo.entities.equipment.EquipmentRenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRenterDao extends JpaRepository<EquipmentRenterEntity, String>,
        JpaSpecificationExecutor<EquipmentEntity> {

    Optional<EquipmentRenterEntity> findByEquipment(EquipmentEntity equipment);
}
