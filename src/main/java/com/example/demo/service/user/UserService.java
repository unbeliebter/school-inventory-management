package com.example.demo.service.user;

import com.example.demo.daos.UserDao;
import com.example.demo.entities.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao dao;

    public List<UserEntity> getAll() {
        return dao.findAll();
    }

    public UserEntity createOrUpdate(UserEntity entity) {
        return dao.save(entity);
    }

    public UserEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }
}
