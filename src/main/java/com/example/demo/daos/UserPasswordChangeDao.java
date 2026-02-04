package com.example.demo.daos;

import com.example.demo.entities.UserPasswordChangeEntity;
import com.example.demo.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPasswordChangeDao  extends JpaRepository<UserPasswordChangeEntity, String> {

    List<UserPasswordChangeEntity> findByUserId(UserEntity user);

    void deleteAllByUserId(UserEntity user);
}
