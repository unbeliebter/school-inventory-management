package com.example.demo.daos;

import com.example.demo.entities.OrganizationalUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationalUnitDao extends JpaRepository<OrganizationalUnitEntity, String> {
}
