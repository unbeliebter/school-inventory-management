package de.schoolinventorymanagement.service.user.services;

import de.schoolinventorymanagement.daos.UserDao;
import de.schoolinventorymanagement.daos.UserPasswordChangeDao;
import de.schoolinventorymanagement.entities.UserPasswordChangeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class UserPasswordChangeService  {
    // TODO check if needed -> implements IPageService<UserPasswordChangeEntity>
    @Autowired
    private UserPasswordChangeDao changeDao;
    @Autowired
    private UserDao userDao;

    public List<UserPasswordChangeEntity> getAll() {
        return changeDao.findAll();
    }


    public UserPasswordChangeEntity create(UserPasswordChangeEntity entity) {
        // Kein Save, da Trigger
        return null;
    }

    public List<UserPasswordChangeEntity> getByUserId(String userId) {
        var user = userDao.findById(userId).orElseThrow();
        return changeDao.findByUserId(user);
    }


    public UserPasswordChangeEntity getById(String id) {
        return changeDao.findById(id).orElse(null);
    }


    public void deleteById(String id) {
        //No delete cause of logging function
    }


    public void writeToCsv(List<UserPasswordChangeEntity> entities, PrintWriter writer) {
        //No Csv Export, cause hashed passwords (Datenschutz)
    }
}
