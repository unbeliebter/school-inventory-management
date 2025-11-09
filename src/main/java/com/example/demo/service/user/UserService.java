package com.example.demo.service.user;

import com.example.demo.daos.UserDao;
import com.example.demo.entities.user.UserEntity;
import org.mindrot.jbcrypt.BCrypt;
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

    public UserEntity create(UserEntity entity) {
        entity.setPassword(hashPassword(entity.getPassword()));
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

    public boolean authenticate(String username, String password) {
        if (username == null) {
            return false;
        }

        if (password == null) {
            return false;
        }

        var user = dao.findByUsername(username);

        if (user.isPresent()) {
            if (username.equals(user.get().getUsername())) {
                return checkPassword(password, user.get().getPassword());
            }
        }

        return false;
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
