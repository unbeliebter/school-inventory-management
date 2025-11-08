package com.example.demo.daos;

import com.example.demo.entities.ResponsibilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsibilityDao extends JpaRepository<ResponsibilityEntity, String> {
}
