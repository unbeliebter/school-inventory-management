package com.example.demo.service.user.services;

import com.example.demo.daos.UserDao;
import com.example.demo.daos.UserPasswordChangeDao;
import com.example.demo.entities.UserPasswordChangeEntity;
import com.example.demo.service.IPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class UserPasswordChangeService implements IPageService<UserPasswordChangeEntity> {

    @Autowired
    private UserPasswordChangeDao changeDao;
    @Autowired
    private UserDao userDao;

    public List<UserPasswordChangeEntity> getAll() {
        return changeDao.findAll();
    }

    @Override
    public UserPasswordChangeEntity create(UserPasswordChangeEntity entity) {
        // Kein Save, da Trigger
        return null;
    }

    public List<UserPasswordChangeEntity> getByUserId(String userId) {
        var user = userDao.findById(userId).orElseThrow();
        return changeDao.findByUserId(user);
    }

    @Override
    public UserPasswordChangeEntity getById(String id) {
        return changeDao.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        //No delete cause of logging function
    }

    @Override
    public void writeToCsv(List<UserPasswordChangeEntity> entities, PrintWriter writer) {
        //No Csv Export, cause hashed passwords (Datenschutz)
    }
}
