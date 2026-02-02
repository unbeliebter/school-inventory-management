package com.example.demo.service.user.mapper;

import com.example.demo.daos.UserDao;
import com.example.demo.entities.user.UserEntity;
import com.example.demo.service.user.UserRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleMapper roleMapper;

    public UserEntity mapToEntityCreate(UserRequest request, UserEntity entity) {
        entity.setFirstname(request.getFirstName());
        entity.setLastname(request.getLastName());
        entity.setEmail(request.getEmail());
        entity.setPassword(hashPassword(request.getPassword()));
        entity.setUsername(generateUsername(request.getFirstName(), request.getLastName()));
        entity.setRole(roleMapper.mapToEntity(request.getRole()));

        return entity;
    }

    public UserEntity mapToEntity(UserRequest request, UserEntity entity) {
        entity.setFirstname(request.getFirstName());
        entity.setLastname(request.getLastName());
        entity.setEmail(request.getEmail());
        entity.setUsername(request.getUsername());
        entity.setRole(roleMapper.mapToEntity(request.getRole()));

        return entity;
    }

    public UserEntity mapToEntityDetailed(UserRequest request) {
        var entity = new UserEntity();
        entity.setId(request.getId());

        return mapToEntity(request, entity);
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String generateUsername(String firstName, String lastName) {
        var count = 1;
        var checkUsername = "";
        var validUsername = false;

        var baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        checkUsername = baseUsername;

        while (!validUsername) {
            var user = userDao.findByUsername(checkUsername);

            if (user.isPresent()) {
                checkUsername = baseUsername + "_" + count;
                count++;

            } else {
                validUsername = true;
            }
        }

        return checkUsername;
    }
}
