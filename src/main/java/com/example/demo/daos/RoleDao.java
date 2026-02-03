package com.example.demo.daos;

import com.example.demo.entities.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<RoleEntity, String> {

    Optional<RoleEntity> findByFrontendName(String roleId);
}
