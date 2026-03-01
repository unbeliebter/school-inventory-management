package de.schoolinventorymanagement.daos;

import de.schoolinventorymanagement.entities.UserPasswordChangeEntity;
import de.schoolinventorymanagement.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPasswordChangeDao  extends JpaRepository<UserPasswordChangeEntity, String> {

    List<UserPasswordChangeEntity> findByUserId(UserEntity user);

    void deleteAllByUserId(UserEntity user);
}
