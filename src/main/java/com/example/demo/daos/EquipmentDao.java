package com.example.demo.daos;

import com.example.demo.entities.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentDao extends JpaRepository<EquipmentEntity, String> {
}
