package com.example.demo.daos;

import com.example.demo.entities.OrganizationalGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationalGroupDao extends JpaRepository<OrganizationalGroupEntity, String> {
}
