package com.example.demo.service.user.services;

import com.example.demo.daos.UserDao;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.IPageService;
import com.example.demo.service.user.mapper.UserMapper;
import com.example.demo.service.user.UserRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class UserService implements IPageService<UserEntity> {

    @Autowired
    private UserDao dao;
    @Autowired
    private UserMapper mapper;

    public List<UserEntity> getAll() {
        return dao.findAll();
    }

    @Transactional
    public UserEntity create(UserRequest request) {
        var entity = mapper.mapToEntityCreate(request, new UserEntity());
        return dao.save(entity);
    }

    @Transactional
    public UserEntity create(UserEntity entity) {
        return dao.save(entity);
    }

    public UserEntity getById(String id) {
        return dao.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(String id) {
        var toDelete = dao.findById(id);

        if (toDelete.isPresent()) {
            dao.deleteById(id);
        }
    }

    @Override
    public void writeToCsv(List<UserEntity> entities, PrintWriter writer) {
        // TODO;
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
                return UserMapper.checkPassword(password, user.get().getPassword());
            }
        }

        return false;
    }

    public UserEntity findByUsername(String username) {
        return dao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username can't be found"));
    }
}
