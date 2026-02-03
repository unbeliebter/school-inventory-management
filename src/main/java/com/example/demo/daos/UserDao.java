package com.example.demo.daos;

import com.example.demo.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

    String id(String id);
}
