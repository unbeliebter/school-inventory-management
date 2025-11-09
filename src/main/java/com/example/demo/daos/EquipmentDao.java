package com.example.demo.daos;

import com.example.demo.entities.equipment.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentDao extends JpaRepository<EquipmentEntity, String>,
        JpaSpecificationExecutor<EquipmentEntity> {
}
